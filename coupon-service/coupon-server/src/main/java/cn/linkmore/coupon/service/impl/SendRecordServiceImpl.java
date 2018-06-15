package cn.linkmore.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.CouponClusterMapper;
import cn.linkmore.coupon.dao.cluster.SendRecordClusterMapper;
import cn.linkmore.coupon.dao.cluster.SendUserClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateEnClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateItemClusterMapper;
import cn.linkmore.coupon.dao.master.CouponMasterMapper;
import cn.linkmore.coupon.dao.master.SendRecordMasterMapper;
import cn.linkmore.coupon.dao.master.SendUserMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemEnMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateMasterMapper;
import cn.linkmore.coupon.entity.Coupon;
import cn.linkmore.coupon.entity.SendRecord;
import cn.linkmore.coupon.entity.SendUser;
import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.request.ReqSendRecord;
import cn.linkmore.coupon.response.ResSendRecord;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.SendRecordService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
@Transactional
public class SendRecordServiceImpl implements SendRecordService {
	@Autowired
	private SendRecordClusterMapper sendRecordClusterMapper;
	
	@Autowired
	private SendRecordMasterMapper sendRecordMasterMapper;
	
	@Resource
	private RedisService redisService;
	@Autowired
	private TemplateItemClusterMapper templateItemClusterMapper;
	@Autowired
	private TemplateEnClusterMapper templateEnClusterMapper;
	@Autowired
	private TemplateItemEnMasterMapper templateItemEnMasterMapper;
	/*@Autowired
	private EnterpriseDealMapper enterpriseDealMapper;*/
	
	@Autowired
	private TemplateClusterMapper templateClusterMapper;
	
	@Autowired
	private TemplateMasterMapper templateMasterMapper;
	@Autowired
	private SendUserClusterMapper sendUserClusterMapper;
	
	@Autowired
	private SendUserMasterMapper sendUserMasterMapper;
	@Autowired
	private CouponClusterMapper couponClusterMapper;
	
	@Autowired
	private CouponMasterMapper couponMasterMapper;
	
	/*@Autowired
	private SmsClient smsClient;*/
	@Autowired
	private UserClient userClient;
	
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.sendRecordClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResSendRecord> list = this.sendRecordClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	public int save(ReqSendRecord record) {
		ResTemplate temp = this.templateClusterMapper.findById(record.getTemplateId());
		/*Subject subject = SecurityUtils.getSubject();
		Person person = (Person) subject.getSession().getAttribute("person");
		record.setCreatorId(person.getId().intValue());
		record.setCreatorName(person.getUsername());*/
		record.setCreateTime(new Date());
		Map<String,Map<String,String>> paramList = new HashMap<>();
		
		String[] list = record.getPhoneJson().split(",");
		Set<String> set = new HashSet<String>();
		for (String phone : list) {
			if (phone != "") {
				set.add(phone);
			}
		}
		if (record.getType().equals(0)) {
			record.setStatus(1);
			record.setSendTime(new Date());
			SendRecord sendRecord = ObjectUtils.copyObject(record, new SendRecord());
			this.sendRecordMasterMapper.save(sendRecord);
			List<SendUser> sendUserList = new ArrayList<SendUser>();
			for (String phone : set) {
				//User user = getUser(phone);
				SendUser couponSendUser = new SendUser();
				couponSendUser.setCreateTime(new Date());
				//couponSendUser.setUserId(user.getId());
				couponSendUser.setRecordId(record.getId());
				//couponSendUser.setUsername(user.getUsername());
				couponSendUser.setTemplateId(record.getTemplateId());
				sendUserList.add(couponSendUser);
			}
			this.sendUserMasterMapper.insertBatch(sendUserList);
			List<ResTemplateItem> items = templateItemClusterMapper.findList(record.getTemplateId());
			List<Coupon> couponList = new ArrayList<Coupon>();
			Coupon coupon = null;
			for(SendUser couponSendUser :sendUserList){
				for (ResTemplateItem item : items) {
					//停车券里配置多少张发送多少张
					for (int i = 0; i < item.getQuantity(); i++) {
						coupon = new Coupon();
						coupon.setUserId(couponSendUser.getUserId());
						coupon.setConditionId(record.getConditionId());
						coupon.setTemplateId(record.getTemplateId());
						coupon.setRecordId(record.getId());
						coupon.setItemId(item.getId());
						coupon.setType(item.getType());
						coupon.setFaceAmount(item.getFaceAmount());
						coupon.setDiscount(item.getDiscount());
						coupon.setConditionAmount(item.getConditionAmount());
						coupon.setValidTime(DateUtils.getDate(new Date(), 0, 0, item.getValidDay(), 0, 0, 0));
						coupon.setStatus((short) 0);
						coupon.setCreateTime(new Date());
						coupon.setSendUserId(couponSendUser.getId());
						couponList.add(coupon);
					}
				}
			}
			couponMasterMapper.insertBatch(couponList);
			
			for(SendUser sendUser :sendUserList){
				Map<String, String> param = new HashMap<String, String>();
				param.put("money", temp.getUnitAmount().toString());
				param.put("enterprise","凌猫停车");
				paramList.put(sendUser.getUsername(), param);
			}
		} else {
			record.setStatus(0);
			SendRecord sendRecord = ObjectUtils.copyObject(record, new SendRecord());
			this.sendRecordMasterMapper.save(sendRecord);
			// 定时发送时发送人手机号存放在redis里面
			if (StringUtils.isNotBlank(record.getPhoneJson())) {
				redisService.set(Constants.RedisKey.COUPON_SEND_RECORD_MOBILE.key + record.getId(),
						record.getPhoneJson());
			}
		}
		//更新发送用户数量
		temp.setSendQuantity(temp.getSendQuantity()+set.size());
		Template template = ObjectUtils.copyObject(temp, new Template());
		this.templateMasterMapper.update(template);
		sendMessage(paramList);
		return 0;
	}

	@Override
	public int saveBusiness(ReqSendRecord record) {/*
		Subject subject = SecurityUtils.getSubject();
		Person person = (Person) subject.getSession().getAttribute("person");
		record.setCreatorId(person.getId().intValue());
		record.setCreatorName(person.getUsername());
		record.setCreateTime(new Date());
		record.setType(0);
		record.setSendTime(new Date());
		record.setStatus(1);
		
		SendRecord sendRecord = ObjectUtils.copyObject(record, new SendRecord());
		this.sendRecordMasterMapper.save(sendRecord);
		ResTemplate template = this.templateEnClusterMapper.findById(record.getTemplateId());
		List<ResTemplateItem> list = this.templateItemEnClusterMapper.findList(template.getId());
		//EnterpriseDeal deal = this.enterpriseDealClusterMapper.findByDealNumber(template.getEnterpriseDealNumber());
		Set<String> set = new HashSet<String>();
		JSONArray jsonArray = JSONObject.parseArray(record.getPhoneJson());
		for (Object object : jsonArray) {
			Map<Object, Object> map =  (Map<Object, Object>)object;
			set.add(map.get("phone").toString());
		}
		//	判断优惠劵是否大于合同金额
		BigDecimal dealPayAmount = new BigDecimal(new BigDecimal(deal.getDealPayAmount()).subtract(new BigDecimal(deal.getUsedDealPayAmount())).toString());	//	支付金额
		BigDecimal dealGiftAmount = new BigDecimal(new BigDecimal(deal.getDealGiftAmount()).subtract(new BigDecimal(deal.getUserDealGiftAmount())).toString());  //	赠送金额
		int sendQuantityCount = 0;
		dealPayAmount = dealPayAmount.subtract(new BigDecimal(template.getContractAmount()).multiply(new BigDecimal(set.size())));
		dealGiftAmount = dealGiftAmount.subtract(new BigDecimal(template.getGivenAmount()).multiply(new BigDecimal(set.size())));
		if(dealPayAmount.compareTo(BigDecimal.ZERO) < 0){
			throw new RuntimeException("订单金额不足0");
		}
		if(dealGiftAmount.compareTo(BigDecimal.ZERO) < 0){
			throw new RuntimeException("赠送金额不足1");
		}
		List<Coupon> coupons = new ArrayList<>();
		dealPayAmount = new BigDecimal(deal.getUsedDealPayAmount());
		dealGiftAmount = new BigDecimal(deal.getUserDealGiftAmount());
		dealPayAmount = dealPayAmount.add(new BigDecimal(template.getContractAmount()).multiply(new BigDecimal(set.size())));
		dealGiftAmount = dealGiftAmount.add(new BigDecimal(template.getGivenAmount()).multiply(new BigDecimal(set.size())));
		List<CouponSendUser> couponUsers = new ArrayList<>();
		Map<String,Map<String,String>> paramList = new HashMap<>();
		List<String> username = new ArrayList<>();
		for (String string : set) {
			User user = getUser(string);
			username.add(user.getUsername());
			CouponSendUser couponSendUser = new CouponSendUser();
			couponSendUser.setCreateTime(new Date());
			couponSendUser.setUserId(user.getId());
			couponSendUser.setRecordId(record.getId());
			couponSendUser.setTemplateId(record.getTemplateId());
			couponUsers.add(couponSendUser);
		}
		this.couponSendUserMapper.insertBatch(couponUsers);
		for (int i = 0 ; i < couponUsers.size() ;i++) {
			for (CouponTemplateItem ct : list) {
				for(int y = 0 ; y < ct.getQuantity();y++){
					Coupon coupon = new Coupon();
					coupon.setCreateTime(new Date());
					coupon.setTemplateId(record.getTemplateId());
					coupon.setUserId(couponUsers.get(i).getUserId());
					coupon.setRecordId(record.getId());
					coupon.setEnterpriseId(template.getEnterpriseId());
					coupon.setItemId(ct.getId());
					coupon.setDiscount(ct.getDiscount());
					coupon.setConditionAmount(ct.getConditionAmount());
					coupon.setType(ct.getType());
					coupon.setFaceAmount(ct.getFaceAmount());
					coupon.setSendUserId(couponUsers.get(i).getId());
					coupon.setStatus((short)0);
					//	计算过期时间
					coupon.setValidTime(DateUtils.getDate(new Date(), 0, 0, ct.getValidDay(), 0, 0, 0));
					coupons.add(coupon);
				}
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("money", template.getUnitAmount().toString());
			if(deal.getEnterpriseName() != null){
				param.put("enterprise",deal.getEnterpriseName());
			}else{
				param.put("enterprise","凌猫停车");
			}
			paramList.put(username.get(i), param);
			sendQuantityCount++;
			//	发送短信通知
			//smsService.send(username.get(i), 8,  SmsTemplateId.share_coupon_code, param);
		}
		this.couponMapper.insertBatch(coupons);
		if(template.getSendQuantity() != null){
			template.setSendQuantity(sendQuantityCount+template.getSendQuantity());
		}else{
			template.setSendQuantity(sendQuantityCount);
		}
		template.setStartTime(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Date expiryTime = DateUtils.getPast2String(template.getValidDay(),calendar );
		template.setExpiryTime(expiryTime);
		this.couponTemplateEnService.update(template);
		//	更新合同已使用金额
		deal.setUsedDealPayAmount(dealPayAmount.doubleValue());
		deal.setUserDealGiftAmount(dealGiftAmount.doubleValue());
		this.enterpriseDealMapper.updateByPrimaryKey(deal);
		sendMessage(paramList);
	*/
		return 0;
	}

	
	
	private void sendMessage(Map<String, Map<String, String>> paramList) {
		new Runnable() {
			@Override
			public void run() {
				Set<Entry<String, Map<String, String>>> entrySet = paramList.entrySet();
				for (Entry<String, Map<String, String>> entry : entrySet) {
					//smsService.send(entry.getKey(), 8,  SmsTemplateId.share_coupon_code, entry.getValue());
				}
			}
		}.run();
	}

	/*private ResUser getUser(String phone){
		ResUser user = null;
		Map<String, Object> param = new HashMap<>();
		param.put("userName", phone);
		user = this.userClient..getUserByUserName(param);
		if (user != null) {
			return user;
		} else {
			user = new User();
			user.setMobile(phone);
			user.setUsername(phone);
			user.setStatus(0);
			user.setUserType(1);
			user.setCreateTime(new Date());
			this.userMapper.insertAndGetId(user);
			return user;
		}
	}*/
	@Override
	public void timingForSend() {/*
		List<CouponSendRecord> recordList = this.couponSendRecordMapper.findTaskList();
		for(CouponSendRecord couponSendRecord :recordList){
			if(couponSendRecord.getTaskTime().compareTo(new Date()) <= 0 ){
				String phoneJson = this.redisTemplate.opsForValue().get(RedisKey.COUPON_SEND_RECORD_MOBILE + couponSendRecord.getId());
				String[] list = phoneJson.split(",");
				Set<String> set = new HashSet<String>();
				for (String phone : list) {
					if (phone != "") {
						set.add(phone);
					}
				}
				CouponTemplate temp = this.couponTemplateMapper.find(couponSendRecord.getTemplateId());
				List<CouponTemplateItem> items = couponTemplateItemMapper.findList(couponSendRecord.getTemplateId());
				List<CouponSendUser> sendUserList = new ArrayList<CouponSendUser>();
				for (String phone : set) {
					User user = getUser(phone);
					CouponSendUser couponSendUser = new CouponSendUser();
					couponSendUser.setCreateTime(new Date());
					couponSendUser.setUserId(user.getId());
					couponSendUser.setRecordId(couponSendRecord.getId());
					couponSendUser.setUsername(user.getUsername());
					couponSendUser.setTemplateId(couponSendRecord.getTemplateId());
					sendUserList.add(couponSendUser);
				}
				this.couponSendUserMapper.insertBatch(sendUserList);
				
				List<Coupon> couponList = new ArrayList<Coupon>();
				Coupon coupon = null;
				for(CouponSendUser couponSendUser :sendUserList){
					
					for (CouponTemplateItem item : items) {
						//停车券里配置多少张发送多少张
						for (int i = 0; i < item.getQuantity(); i++) {
							coupon = new Coupon();
							coupon.setUserId(couponSendUser.getUserId());
							coupon.setConditionId(couponSendRecord.getConditionId());
							coupon.setTemplateId(couponSendRecord.getTemplateId());
							coupon.setRecordId(couponSendRecord.getId());
							coupon.setItemId(item.getId());
							coupon.setType(item.getType());
							coupon.setFaceAmount(item.getFaceAmount());
							coupon.setDiscount(item.getDiscount());
							coupon.setConditionAmount(item.getConditionAmount());
							coupon.setValidTime(DateUtils.getDate(new Date(), 0, 0, item.getValidDay(), 0, 0, 0));
							coupon.setStatus((short) 0);
							coupon.setCreateTime(new Date());
							coupon.setSendUserId(couponSendUser.getId());
							couponList.add(coupon);
						}
					}
				}
				couponMapper.insertBatch(couponList);
				for(CouponSendUser couponSendUser :sendUserList){
					Map<String, String> param = new HashMap<String, String>();
					param.put("money", temp.getUnitAmount().toString());
					param.put("enterprise","凌猫停车");
					smsService.send(couponSendUser.getUsername(), 8,  SmsTemplateId.share_coupon_code, param);
				}
				
				//已结束
				couponSendRecord.setStatus(1);
				couponSendRecord.setSendTime(new Date());
				this.couponSendRecordMapper.updateByPrimaryKey(couponSendRecord);
				redisTemplate.delete(RedisKey.USER_GROUP_IDS+couponSendRecord.getId());
			}
		}
	*/}
}
