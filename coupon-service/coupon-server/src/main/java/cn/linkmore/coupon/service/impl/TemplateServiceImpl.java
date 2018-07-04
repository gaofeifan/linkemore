package cn.linkmore.coupon.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
import cn.linkmore.coupon.dao.cluster.TemplateClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateItemClusterMapper;
import cn.linkmore.coupon.dao.master.QrcMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateMasterMapper;
import cn.linkmore.coupon.entity.Qrc;
import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateService;
import cn.linkmore.third.client.WechatClient;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	private TemplateClusterMapper templateClusterMapper;
	
	@Autowired
	private TemplateMasterMapper templateMasterMapper;
	
	@Autowired
	private TemplateItemClusterMapper templateItemClusterMapper;
	
	@Autowired
	private TemplateItemMasterMapper templateItemMasterMapper;

	@Autowired
	private QrcMasterMapper qrcMasterMapper;
	
	@Autowired
	private BaseAttachmentClient baseAttachmentClient;
	
	@Autowired
	private WechatClient wechatClient;
	
	// 二维码  
	private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
	// 通过ticket换取二维码  
	private String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.templateClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		
		
		List<ResTemplate> list = this.templateClusterMapper.findPage(param);
		if(list.size() == 0){
			return new ViewPage(0,1,new ArrayList<ResTemplate>());
		}
		List<Long> ids = new ArrayList<Long>();
		for(ResTemplate couponTemp: list){
			ids.add(couponTemp.getId());
		}
		List<ResTemplateItem> itemList = templateItemClusterMapper.findItemList(ids);
		Map<Long, List<ResTemplateItem>> map = itemList.stream().collect(Collectors.groupingBy(ResTemplateItem::getTemplateId));
		
		for(ResTemplate couponTemp: list){
			String discount = "";
			List<ResTemplateItem> items =  map.get(couponTemp.getId());
			if(CollectionUtils.isNotEmpty(items)){
				for(ResTemplateItem item :items){
					if (Integer.valueOf(0).equals(item.getType())) {
						discount +="立减券: "+ item.getFaceAmount().doubleValue()+"元" + item.getQuantity()+"张        ";
					}else if(Integer.valueOf(1).equals(item.getType())){
						discount +="满减券: "+ item.getConditionAmount().doubleValue()+"元减" + item.getFaceAmount().doubleValue()+"元        ";
					}else if(Integer.valueOf(2).equals(item.getType())){
						discount +="折扣券: "+ item.getDiscount()/10.0 +"折" + item.getQuantity()+"张        ";
					}
				}
				couponTemp.setDiscount(discount);
				couponTemp.setRemainNumber(String.valueOf(couponTemp.getTotalQuantity()-couponTemp.getSendQuantity()));
			}
		}
		
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqTemplate reqTemp) {
		/*record.setCreatorId(person.getId().intValue());
		record.setCreatorName(person.getUsername());*/
		reqTemp.setCreateTime(new Date());
		reqTemp.setUpdateTime(new Date());
		Template temp = ObjectUtils.copyObject(reqTemp, new Template());
		this.templateMasterMapper.save(temp);
		Integer unitCount = 0;
		BigDecimal unitAmount = new BigDecimal(0);
		JSONArray jsonArray = JSONObject.parseArray(reqTemp.getDiscount());
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				TemplateItem item = initCouponTemplateItem(json);
				item.setTemplateId(temp.getId());
				unitCount += item.getQuantity();
				unitAmount = unitAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				templateItemMasterMapper.save(item);
			}
		}
		temp.setSendQuantity(0);
		temp.setUnitCount(unitCount);
		temp.setUnitAmount(unitAmount);
		temp.setTotalAmount(temp.getUnitAmount().multiply(new BigDecimal(temp.getTotalQuantity())));
		return this.templateMasterMapper.update(temp);
	}
	
	public TemplateItem initCouponTemplateItem(JSONObject json) {
		TemplateItem item = new TemplateItem();
		if (json != null) {
			Integer type = json.getInteger("couponItemType");
			BigDecimal faceAmount = new BigDecimal(0);
			if (type == 0) {
				faceAmount = json.getBigDecimal("faceAmount");
			} else if (type == 1) {
				faceAmount = json.getBigDecimal("mj_faceAmount");
			} else if (type == 2) {
				faceAmount = json.getBigDecimal("zk_faceAmount");
			}
			Integer quantity = json.getInteger("quantity");
			Integer validDay = json.getInteger("couponValidDay");
			Integer discount = json.getInteger("item_discount");
			BigDecimal conditionAmount = json.getBigDecimal("conditionAmount");
			item.setConditionAmount(conditionAmount);
			item.setDiscount(discount);
			item.setFaceAmount(faceAmount);
			item.setType(type);
			item.setQuantity(quantity);
			item.setValidDay(validDay);
			item.setDeleteStatus(0);
		}
		return item;
	}
	
	public TemplateItem initEditCouponTemplateItem(JSONObject json) {
		TemplateItem item = new TemplateItem();
		if (json != null) {
			Long id = json.getLong("itemId");
			Integer type = json.getInteger("couponItemType");
			BigDecimal faceAmount = new BigDecimal(0);
			if (type == 0) {
				faceAmount = json.getBigDecimal("faceAmount");
			} else if (type == 1) {
				faceAmount = json.getBigDecimal("mj_faceAmount");
			} else if (type == 2) {
				faceAmount = json.getBigDecimal("zk_faceAmount");
			}
			Integer quantity = json.getInteger("quantity");
			Integer validDay = json.getInteger("couponValidDay");
			Integer discount = json.getInteger("item_discount");
			BigDecimal conditionAmount = json.getBigDecimal("conditionAmount");
			item.setId(id);
			item.setConditionAmount(conditionAmount);
			item.setDiscount(discount);
			item.setFaceAmount(faceAmount);
			item.setType(type);
			item.setQuantity(quantity);
			item.setValidDay(validDay);
		}
		return item;
	}
	
	@Override
	public int update(ReqTemplate reqTemp) {
		reqTemp.setUpdateTime(new Date());
		Integer unitCount = 0;
		BigDecimal unitAmount = new BigDecimal(0);
		JSONArray jsonArray = JSONObject.parseArray(reqTemp.getDiscount());
		List<ResTemplateItem> items = this.templateItemClusterMapper.findList(reqTemp.getId());
		List<Long> itemIds = new ArrayList<Long>();
		Map<Long,ResTemplateItem> map = new HashMap<Long,ResTemplateItem>();
		for(ResTemplateItem item : items){
			itemIds.add(item.getId());
			map.put(item.getId(), item);
		}
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				TemplateItem item = initEditCouponTemplateItem(json);
				item.setTemplateId(reqTemp.getId());
				item.setDeleteStatus(0);
				unitCount += item.getQuantity();
				unitAmount = unitAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				if(item.getId()!=null){
					itemIds.remove(item.getId());
					templateItemMasterMapper.update(item);
				}else{
					templateItemMasterMapper.save(item);
				}
			}
		}
		for(Long id : itemIds){
			ResTemplateItem item = map.get(id);
			//未启用状态下直接删除操作
			this.templateItemMasterMapper.delete(id);
			/*item.setDeleteStatus(1);
			couponTemplateItemMapper.updateByPrimaryKey(item);*/
		}
		reqTemp.setSendQuantity(0);
		reqTemp.setUnitCount(unitCount);
		reqTemp.setUnitAmount(unitAmount);
		reqTemp.setTotalAmount(reqTemp.getUnitAmount().multiply(new BigDecimal(reqTemp.getTotalQuantity())));
		Template temp = ObjectUtils.copyObject(reqTemp, new Template());
		return this.templateMasterMapper.update(temp);
	}

	@Override
	public int delete(Long id) {
		ResTemplate resTemplate = this.templateClusterMapper.findById(id);
		resTemplate.setDeleteStatus(1);
		Template template = ObjectUtils.copyObject(resTemplate, new Template());
		return this.templateMasterMapper.update(template);
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.templateClusterMapper.check(param); 
	}

	@Override
	public int start(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		
		ResTemplate temp = findById(id);
		if(temp.getStartTime() == null){
			param.put("startTime", new Date());
			if(temp.getValidType() ==  1 && temp.getValidDay() != null){
				//根据开启时间 + 有效天数 = 计算到期时间
				Date expiryDate = DateUtils.getDate(new Date(), 0, 0, temp.getValidDay(), 0, 0, 0);
				param.put("expiryTime", expiryDate);
			}
			Qrc qrc = new Qrc();
			qrc.setCreateTime(new Date());
			qrc.setName(temp.getName());
			if(temp.getCreatorId()!=null) {
				qrc.setOperatorId(temp.getCreatorId().longValue());
			}
			qrc.setStatus((short) 1L);
			qrc.setTemplateId(temp.getId());
			this.qrcMasterMapper.save(qrc);
			
			String ticket = wechatClient.getTicket(QR_LIMIT_SCENE, qrc.getId());
			if(!StringUtils.isNotBlank(ticket)){
				throw new DataException("生成二维码失败");
			}
			qrc.setTicket(ticket);
			try {
				String originalUrl = downloadFile(showqrcode_path+URLEncoder.encode(ticket, "utf-8"));
				qrc.setUrl("http://oss.pabeitech.com/"+originalUrl);
				this.qrcMasterMapper.update(qrc);
			} catch (Exception e) {
				throw new DataException("下载二维码失败");
			}
		}
		
		param.put("id", id);
		param.put("status", 1);
		param.put("updateTime", new Date());
		return this.templateMasterMapper.startOrStop(param);
	}
	
	private String downloadFile(String urlString){
		String name = new Date().getTime()+".jpg";
		String originalUrl = "";
		URL url;
		try {
			url = new URL(urlString);
			URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            originalUrl = createImage(name,is);
            is.close();
		} catch (Exception  e) {
			e.printStackTrace();
		}
		return originalUrl;
	}
	private String createImage(String fileName,InputStream is) {
		return baseAttachmentClient.createImage(fileName, is);
	}

	@Override
	public int stop(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 2);
		param.put("updateTime", new Date());
		return this.templateMasterMapper.startOrStop(param);
	}

	@Override
	public ResTemplate findById(Long id) {
		return this.templateClusterMapper.findById(id);
	}
	/**
	 * 定时任务
	 */
	public int timingForUpdate() {
		Date now= new Date();
		int sum = 0;
		List<ResTemplate> all = templateClusterMapper.findTaskList();
		for (ResTemplate reqTemp : all) {
			if(reqTemp.getExpiryTime().compareTo(now) <= 0 ){
				//已结束
				reqTemp.setStatus(3);
				Template temp = ObjectUtils.copyObject(reqTemp, new Template());
				sum += this.templateMasterMapper.update(temp);
			}
		}
		return sum;
	}

	@Override
	public List<ResTemplate> findSubjectCouponList() {
		return this.templateClusterMapper.findSubjectCouponList();
	}
}
