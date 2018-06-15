package cn.linkmore.coupon.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
import cn.linkmore.coupon.controller.TemplateEnController;
import cn.linkmore.coupon.dao.cluster.QrcClusterMapper;
import cn.linkmore.coupon.dao.cluster.RollbackClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateEnClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateItemEnClusterMapper;
import cn.linkmore.coupon.dao.master.QrcMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateEnMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemEnMasterMapper;
import cn.linkmore.coupon.entity.Qrc;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResRollback;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateEnService;
import cn.linkmore.coupon.utils.CouponUtils;
import cn.linkmore.third.client.WechatClient;
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
	private QrcMasterMapper qrcMasterMapper;
	@Autowired
	private RollbackClusterMapper rollbackClusterMapper;
	@Autowired
	private TemplateItemEnClusterMapper templateItemEnClusterMapper;
	@Autowired
	private TemplateItemEnMasterMapper templateItemEnMasterMapper;
	
	@Autowired
	private BaseAttachmentClient baseAttachmentClient;
	
	/*@Autowired
	private EnterpriseDealMapper enterpriseDealMapper;
	*/
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
	public int save(ReqTemplate record) {
		/*Subject subject = SecurityUtils.getSubject();
		Person person = (Person)subject.getSession().getAttribute("person");
		record.setCreatorId(person.getId().intValue());
		record.setCreatorName(person.getUsername());*/
		record.setCreateTime(new Date());
		record.setMerchantDefault(0);
		record.setUpdateTime(new Date());
		
		this.templateEnMasterMapper.save(record);
		JSONArray jsonArray = JSONObject.parseArray(record.getDiscount());
		ResTemplate resTemp = ObjectUtils.copyObject(record , new ResTemplate());
		resTemp = addItem(jsonArray, resTemp ,0);
		this.templateEnMasterMapper.update(record);
		this.templateItemEnMasterMapper.insertBatch(resTemp.getItems());
		/*EnterpriseDeal deal = enterpriseDealMapper.selectByDealNumber(record.getEnterpriseDealNumber());
		deal.setIsCreate(1);
		this.enterpriseDealMapper.updateByPrimaryKey(deal);*/
		return 0;
	}
	
	@Override
	public int update(ReqTemplate record) {
		return this.templateEnMasterMapper.update(record);
	}

	@Override
	public int delete(Long id) {
		return this.templateEnMasterMapper.deleteById(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.templateEnClusterMapper.check(param); 
	}

	@Override
	public int start(Long id) {
	Map<String,Object> param = new HashMap<String,Object>();
		ResTemplate temp = findById(id);
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
				String originalUrl = downloadFile(showqrcode_path+ URLEncoder.encode(ticket, "utf-8"));
				qrc.setUrl("http://oss.pabeitech.com/"+originalUrl);
				this.qrcMasterMapper.update(qrc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		param.put("id", id);
		param.put("status", 1);
		param.put("updateTime", new Date());
		return this.templateEnMasterMapper.startOrStop(param);
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
		return this.templateEnMasterMapper.startOrStop(param);
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
	public List<ResTemplate> findByEnterpriseId(Long entId) {
		return this.templateEnClusterMapper.findByEnterpriseId(entId);
	}

	@Override
	public int saveBusiness(ReqTemplate record) {
		ResTemplate oct = this.templateEnClusterMapper.findByEnterpriseNumber(record.getEnterpriseDealNumber());
		oct.setMerchantDefault(1);
		oct.setName(record.getName());
		templateItemEnMasterMapper.deleteByTempId(oct.getId());
		JSONArray jsonArray = JSONObject.parseArray(record.getDiscount());
		oct = addItem(jsonArray,oct,1);
		this.templateItemEnMasterMapper.insertBatch(oct.getItems());
		oct.setUpdateTime(new Date());
		ReqTemplate reqTemp = ObjectUtils.copyObject(oct, new ReqTemplate());
		return this.templateEnMasterMapper.update(reqTemp);
	}
	
	
	private ResTemplate addItem(JSONArray jsonArray, ResTemplate oct,int isBusiness){
		BigDecimal totalAmount = new BigDecimal(0);
		Integer unitCount = 0;
		BigDecimal unitAmount = new BigDecimal(0);
		BigDecimal contractAmount = new BigDecimal(0);
		BigDecimal givenAmount = new BigDecimal(0);
		List<ResTemplateItem> items = new ArrayList<>();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				ResTemplateItem item = null;
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
			return oct;
		}
		return oct;
	}
}
