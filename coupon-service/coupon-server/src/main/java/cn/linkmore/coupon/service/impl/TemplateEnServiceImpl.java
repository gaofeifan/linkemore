package cn.linkmore.coupon.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.coupon.controller.TemplateEnController;
import cn.linkmore.coupon.dao.cluster.QrcClusterMapper;
import cn.linkmore.coupon.dao.cluster.RollbackClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateEnClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateItemEnClusterMapper;
import cn.linkmore.coupon.dao.master.QrcMasterMapper;
import cn.linkmore.coupon.dao.master.RollbackMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateEnMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemEnMasterMapper;
import cn.linkmore.coupon.entity.Qrc;
import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResRollback;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateEnService;
import cn.linkmore.coupon.utils.CouponUtils;
import cn.linkmore.prefecture.client.EnterpriseDealClient;
import cn.linkmore.third.client.OssClient;
import cn.linkmore.third.client.WechatClient;
import cn.linkmore.third.response.ResOssConfig;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;


@Service
@Transactional
public class TemplateEnServiceImpl implements TemplateEnService {
	@Autowired
	private TemplateEnClusterMapper templateEnClusterMapper;
	@Autowired
	private TemplateEnMasterMapper templateEnMasterMapper;
	@Autowired
	private TemplateItemEnClusterMapper templateItemEnClusterMapper;
	@Autowired
	private TemplateItemEnMasterMapper templateItemEnMasterMapper;
	@Autowired
	private QrcClusterMapper qrcClusterMapper;
	@Autowired
	private QrcMasterMapper qrcMasterMapper;
	@Autowired
	private RollbackClusterMapper rollbackClusterMapper;
	@Autowired
	private RollbackMasterMapper rollbackMasterMapper;
	@Autowired
	private WechatClient wechatClient;
	@Resource
	private BaseAttachmentClient attachmentClient;
	@Resource
	private EnterpriseDealClient enterpriseDealClient;
	@Resource
	private OssClient client;
	private ResOssConfig ossConfig;
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
		param.put("deleteStatus", 0);
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.templateEnClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResTemplate> list = this.templateEnClusterMapper.findPage(param);
		if(list.size() == 0){
			return new ViewPage(0,1,new ArrayList<ResTemplate>());
		}
		List<String> ids = ObjectUtils.findFieldVlaue(list, "id",null,null);
		List<ResTemplateItem> items =  this.templateItemEnClusterMapper.selectByTemplateIds(ids);
		List<ResRollback> rollbackList =  this.rollbackClusterMapper.findByTemplateIds(ids);
		Map<Long,ResRollback> rollbackMap = new HashMap<Long, ResRollback>();
		for(ResRollback rollback : rollbackList){
			rollbackMap.put(rollback.getTemplateId(), rollback);
		}
		for(ResTemplate couponTemp: list){
			// 已开启启动时间
			if(couponTemp.getStartTime() != null ){
				if( couponTemp.getValidDay() != null){
					double d = DateUtils.daysBetween(couponTemp.getStartTime(), new Date());
					couponTemp.setResidualReleasePeriod(((int)((double)couponTemp.getValidDay() - d))+"");
				}
			}else{
				//	启动时间未开启时 有效周期不计算
				couponTemp.setResidualReleasePeriod(couponTemp.getValidDay().toString());
			}
			Integer type = CouponUtils.getCouponType(items, couponTemp.getId());
			if(type == null){
				couponTemp.setType(null);
			}else{
				couponTemp.setType(type);
			}
			String discount = CouponUtils.joinCouponDetails(items, couponTemp.getId());
			couponTemp.setDiscount(discount);
			
			
			if(rollbackMap.containsKey(couponTemp.getId())){
				couponTemp.setRollbackFlag(1);
			}
		}
		return new ViewPage(count,pageable.getPageSize(),list); 
	}


	@Override
	public void save(ReqTemplate record) {
		/*Subject subject = SecurityUtils.getSubject();
		Person person = (Person)subject.getSession().getAttribute("person");
		record.setCreatorId(person.getId().intValue());
		record.setCreatorName(person.getUsername());
		 */
		record.setCreateTime(new Date());
		record.setMerchantDefault(0);
		record.setUpdateTime(new Date());
		Template temp = ObjectUtils.copyObject(record, new Template(),null,null,new String[] {"items"});
		this.templateEnMasterMapper.save(temp);
		JSONArray jsonArray = JSONObject.parseArray(temp.getDiscount());
		temp = addItem(jsonArray, temp,0,null);
		this.templateEnMasterMapper.updateById(temp);
		this.templateItemEnMasterMapper.insertBatch(temp.getItems());
		Map<String, Object> map = new HashMap<>();
		map.put("isCreate", 1);
		map.put("enterpriseDeal", temp.getEnterpriseDealNumber());
		this.enterpriseDealClient.updateCreateStatus(map );
	}
	
	@Override
	public Template update(ReqTemplate reqRecord) {
			Template record = ObjectUtils.copyObject(reqRecord, new Template());
			List<String> list = null;
			record.setType(1);
			record.setUpdateTime(new Date());
			String string = record.getDeleteItemId();
			if(StringUtils.isNotBlank(string)){
				String[] strings = string.split(",");
				list = Arrays.asList(strings);
			}
			ResTemplate oriTemp = this.templateEnClusterMapper.findById(record.getId());
			/*Subject subject = SecurityUtils.getSubject();
			Person person = (Person)subject.getSession().getAttribute("person");
			record.setCreatorId(person.getId().intValue());
			record.setCreatorName(person.getUsername());*/
			record.setUpdateTime(new Date());
			if(list != null && list.size() != 0){
				Set<String> set = new HashSet<>(list);
				StringBuilder sb = new StringBuilder();
				for (String string2 : set) {
					sb.append(string2).append(",");
				}
				Map<String,Object> map = new HashMap<>();
				map.put("delete_status", 1);
				map.put("ids", sb.substring(0, sb.length()-1));
				this.templateItemEnMasterMapper.updateDeletaStatus(map);
			}
			JSONArray jsonArray = JSONObject.parseArray(record.getDiscount());
			record = addItem(jsonArray, record,0,list);
			List<TemplateItem> deleteItem = new ArrayList<>();
			if(record.getItems() != null){
				for (TemplateItem item : record.getItems()) {
					if(item.getId() != null){
						this.templateItemEnMasterMapper.updateById(item);
						deleteItem.add(item);
					}
				}
			}
			record.getItems().removeAll(deleteItem);
			if(record.getItems() != null  && record.getItems().size() != 0){
				this.templateItemEnMasterMapper.insertBatch(record.getItems());
			}
			this.templateEnMasterMapper.updateByIdSelective(record);
			if(!oriTemp.getEnterpriseDealNumber().equals(record.getEnterpriseDealNumber())){
				Map<String, Object> map = new HashMap<>();
				map.put("isCreate", 1);
				map.put("enterpriseDeal", record.getEnterpriseDealNumber());
				this.enterpriseDealClient.updateCreateStatus(map );

				map.put("isCreate",0);
				map.put("enterpriseDeal", oriTemp.getEnterpriseDealNumber());
				this.enterpriseDealClient.updateCreateStatus(map );
			}
		return record;
	}

	@Override
	public int delete(Long id) {
		return this.templateEnMasterMapper.deleteById(id); 
	}
	
	@Override
	public Integer check(String property,String value,Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", property);
		param.put("value", value);
		param.put("id", id);
		return this.templateEnClusterMapper.check(param); 
	}

	@Override
	public void start(Long id) {
	Map<String,Object> param = new HashMap<String,Object>();
		ResTemplate temp = find(id);
		if(temp.getStartTime() == null){
			param.put("startTime", new Date());
			if(temp.getValidDay() != null){
				//根据开启时间 + 有效天数 = 计算到期时间
				Date expiryDate = DateUtils.getDate(new Date(), 0, 0, temp.getValidDay(), 0, 0, 0);
				param.put("expiryTime", expiryDate);
			}
			Qrc qrc = new Qrc();
			qrc.setCreateTime(new Date());
			qrc.setName(temp.getName());
			qrc.setOperatorId(temp.getCreatorId().longValue());
			qrc.setStatus((short) 1L);
			qrc.setTemplateId(temp.getId());
			this.qrcMasterMapper.save(qrc); 
			String ticket = wechatClient.getTicket(QR_LIMIT_SCENE, qrc.getId());
			qrc.setTicket(ticket);
			try {
				String originalUrl = downloadFile(showqrcode_path+URLEncoder.encode(ticket, "utf-8"));
				qrc.setUrl("http://oss.pabeitech.com/"+originalUrl);
				this.qrcMasterMapper.update(qrc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		param.put("id", id);
		param.put("status", 1);
		param.put("updateTime", new Date());
		this.templateEnMasterMapper.startOrStop(param);
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
		ReqAttachment image = new ReqAttachment();
		//image.setId(uuid.toString());
		image.setCreateTime(new Date()); 
		image.setSource(ReqAttachment.SOURCE_SERVER);
		image.setType(ReqAttachment.TYPE_IMAGE);
		image.setSize(0l);
		image.setName(fileName);
		int index = fileName.lastIndexOf("."); 
		image.setSuffix(fileName.substring(index));
		this.attachmentClient.save(image);
		try { 
			OSSClient ossClient = client.uploadOSSClient();
			ossClient.putObject(getOssConfig().getBucketName(), image.getOriginalUrl(), is); 
		} catch (Exception e) { 
			throw new RuntimeException();
		}finally{
			if(is!=null){try {is.close();} catch (IOException e) {e.printStackTrace();}}  
		} 
		return image.getOriginalUrl();
	}
	@Override
	public void stop(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 2);
		param.put("updateTime", new Date());
		this.templateEnMasterMapper.startOrStop(param);
	}

	@Override
	public ResTemplate findById(Long id) {
		ResTemplate couponTemp = this.templateEnClusterMapper.findById(id);
		List<ResTemplateItem> items = templateItemEnClusterMapper.findList(couponTemp.getId());
		if(couponTemp.getCreateTime() != null && couponTemp.getValidDay() != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(couponTemp.getCreateTime());
			Date date = DateUtils.getPast2String(couponTemp.getValidDay(), calendar );
			couponTemp.setExpiryTime(date);
		}
		String discount = CouponUtils.joinCouponDetailsDiv(items, couponTemp.getId());
		Integer type = CouponUtils.getCouponType(items, couponTemp.getId());
		if(type == null){
			couponTemp.setType(null);
		}else{
			couponTemp.setType(type);
		}
		couponTemp.setDiscount(discount);
		return couponTemp;
	}

	@Override
	public List<ResTemplate> selectByEnterpriseId(Long entId) {
		return this.templateEnClusterMapper.findByEnterpriseId(entId);
	}
	
	@Override
	public ResTemplate find(Long id) {
		return this.templateEnClusterMapper.find(id);
	}

	@Override
	public void saveBusiness(ReqTemplate record) {
		Template oct = this.templateEnClusterMapper.findByEnterpriseNumber(record.getEnterpriseDealNumber());
		oct.setMerchantDefault(1);
		oct.setName(record.getName());
		templateItemEnMasterMapper.deleteByTempId(oct.getId());
		JSONArray jsonArray = JSONObject.parseArray(record.getDiscount());
		oct = addItem(jsonArray,oct,1,null);
		this.templateItemEnMasterMapper.insertBatch(oct.getItems());
		oct.setUpdateTime(new Date());
		this.templateEnMasterMapper.updateById(oct);
	}
	
	
	private Template addItem(JSONArray jsonArray, Template oct,int isBusiness,List<String> list){
		BigDecimal totalAmount = new BigDecimal(0);
		Integer unitCount = 0;
		BigDecimal unitAmount = new BigDecimal(0);
		BigDecimal contractAmount = new BigDecimal(0);
		BigDecimal givenAmount = new BigDecimal(0);
		List<TemplateItem> items = new ArrayList<>();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				if(list == null || (json.get("id") == null || json.get("id").toString() == "") || (!list.contains(json.get("id").toString()))){
				TemplateItem item = null;
				if(isBusiness == 0){
					item = TemplateEnController.initCouponTemplateItem(json);
				}else{
					item = TemplateEnController.initCouponTemplateItem2(json);
				}
				item.setTemplateId(oct.getId());
				// 计算订单总金额
				totalAmount = totalAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				unitCount += item.getQuantity();
				unitAmount = unitAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				if(item.getSource() == 0){
					contractAmount = contractAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				}else if(item.getSource() == 1){
					givenAmount = givenAmount.add(item.getFaceAmount().multiply(new BigDecimal(item.getQuantity())));
				}
				items.add(item);
			}
			oct.setItems(items);
			oct.setContractAmount(contractAmount);
			oct.setGivenAmount(givenAmount);
			oct.setSendQuantity(0);
			oct.setUnitCount(unitCount);
			oct.setUnitAmount(unitAmount);
			oct.setTotalAmount(totalAmount);
			}
		}
		return oct;
	}
	
	private ResOssConfig getOssConfig() {
		if(ossConfig == null) {
			ossConfig = client.initOssConfig();
		}
		return ossConfig;
	}
	
	
}
