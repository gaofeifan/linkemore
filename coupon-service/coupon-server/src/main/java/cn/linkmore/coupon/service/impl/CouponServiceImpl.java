package cn.linkmore.coupon.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.CouponStatus;
import cn.linkmore.bean.common.Constants.CouponType;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.coupon.dao.cluster.CouponClusterMapper;
import cn.linkmore.coupon.dao.cluster.SubjectClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateConditionClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateEnClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateItemClusterMapper;
import cn.linkmore.coupon.dao.master.CouponMasterMapper;
import cn.linkmore.coupon.dao.master.SendRecordMasterMapper;
import cn.linkmore.coupon.dao.master.SendUserMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateMasterMapper;
import cn.linkmore.coupon.entity.Coupon;
import cn.linkmore.coupon.entity.SendRecord;
import cn.linkmore.coupon.entity.SendUser;
import cn.linkmore.coupon.entity.Template;
import cn.linkmore.coupon.entity.TemplateCondition;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCondition;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResCouponPrefecture;
import cn.linkmore.coupon.response.ResDayTime;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.response.ResWeekTime;
import cn.linkmore.coupon.service.CouponService;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.EntBrandAdClient;
import cn.linkmore.prefecture.client.FeignEnterpriseClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StrategyFeeClient;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * Service实现 - 停车券
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class CouponServiceImpl implements CouponService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CouponClusterMapper couponClusterMapper;

	@Autowired
	private CouponMasterMapper couponMasterMapper;

	@Autowired
	private TemplateConditionClusterMapper templateConditionClusterMapper;

	@Autowired
	private PrefectureClient prefectureClient;

	@Autowired
	private StrategyFeeClient strategyFeeClient;
	
	@Autowired
	private RedisService redisService;

	@Autowired
	private OrderClient orderClient;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private TemplateItemClusterMapper templateItemClusterMapper;

	@Autowired
	private TemplateConditionClusterMapper couponThemplateConditionClusterMapper;
	@Autowired
	private SubjectClusterMapper subjectClusterMapper;
	@Autowired
	private TemplateClusterMapper templateClusterMapper;
	@Autowired
	private SendRecordMasterMapper sendRecordMasterMapper;
	@Autowired
	private SendUserMasterMapper sendUserMasterMapper;
	@Autowired
	private TemplateMasterMapper templateMasterMapper;
	@Resource
	private UserClient userClient;
	
	@Autowired
	private TemplateEnClusterMapper templateEnClusterMapper;
	
	@Autowired
	private FeignEnterpriseClient enterpriseClient;
	
	@Autowired
	private EntBrandAdClient entBrandAdClient;

	private static final short WEEK_TIME = 1;
	private static final short DAY_TIME = 2;

	private void addCondition(Map<Long, List<ResCoupon>> ucrbsMap) {
		Set<Long> keys = ucrbsMap.keySet();
		List<Long> ids = new ArrayList<Long>();
		for (Long key : keys) {
			ids.add(key.longValue());
		}
		log.info("{}", JsonUtil.toJson(ids));
		List<TemplateCondition> list = this.couponThemplateConditionClusterMapper.findTemplateList(ids);
		log.info("list:{}", JsonUtil.toJson(list));
		Map<Long, TemplateCondition> ccMap = new HashMap<Long, TemplateCondition>();
		String json = null;
		ids = new ArrayList<Long>();
		Map<Long, ResCondition> crbMap = new HashMap<Long, ResCondition>();
		ResCondition crb = null;
		List<ResCondition> crbs = new ArrayList<ResCondition>();
		for (TemplateCondition cc : list) {
			ccMap.put(cc.getId(), cc);
			crb = new ResCondition();
			crb.setPreLimit(cc.getAvailablePrefecture().shortValue());
			crb.setTimeLimit(cc.getAvailableTime().shortValue());
			if (cc.getAvailablePrefecture().shortValue() > 0) {
				String redis = null;
				redis = (String) this.redisService
						.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key + cc.getId().toString());
				log.info("redis:{}", JsonUtil.toJson(redis));
				if (StringUtils.isNotBlank(redis)) {
					String[] preids = redis.split(",");
					crb.setPrefectures(preids);
					for (String id : preids) {
						ids.add(Long.valueOf(id));
					}
				}
			}
			if (cc.getAvailableTime().shortValue() > 0) {
				json = (String) this.redisService
						.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key + cc.getId().toString());
				if (cc.getAvailableTime().shortValue() == WEEK_TIME) {
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					crb.setWtb(wb);

				} else if (cc.getAvailableTime().shortValue() == DAY_TIME) {
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					crb.setDtb(db);
				}
			}
			crbMap.put(cc.getId(), crb);
			crbs.add(crb);
		}
		if (ids.size() > 0) {
			List<ResCouponPrefecture> ps = null;
			log.info("ids:{}", JsonUtil.toJson(ids));
			List<ResPre> rps = this.prefectureClient.prenames(ids);
			if (!CollectionUtils.isEmpty(rps)) {
				ps = new ArrayList<ResCouponPrefecture>();
				ResCouponPrefecture rcp = null;
				for (ResPre rp : rps) {
					rcp = new ResCouponPrefecture();
					rcp.setId(rp.getId());
					rcp.setName(rp.getName());
					ps.add(rcp);
				}
			}
			Map<String, ResCouponPrefecture> pmap = new HashMap<String, ResCouponPrefecture>();
			for (ResCouponPrefecture p : ps) {
				pmap.put(p.getId().toString(), p);
			}
			List<ResCoupon> ucrbs = null;
			ResCouponPrefecture p = null;
			for (Long key : keys) {
				ucrbs = ucrbsMap.get(key);
				crb = crbMap.get(key.longValue());
				for (ResCoupon ucrb : ucrbs) {
					ucrb.setCrb(crb);
					if (crb.getPreLimit() > 0) {
						ucrb.setPreLimit((short) 1);
						if (crb.getPrefectures() != null && crb.getPrefectures().length > 0) {
							p = pmap.get(crb.getPrefectures()[0]);
							if (p != null) {
								ucrb.setPreName(p.getName());
							}
							StringBuffer sb = new StringBuffer();
							for (String pid : crb.getPrefectures()) {
								p = pmap.get(pid);
								if (p != null) {
									sb.append("、");
									sb.append(p.getName());
								}
							}
							if (sb.length() > 0) {
								ucrb.setPreNameList(sb.toString().substring(1));
							}
						}
					}

				}
			}
		}

	}

	@Override
	public List<ResCoupon> userEnabledList(Long userId) {
		List<ResCoupon> list = this.couponClusterMapper.findEnabledList(userId);
		Map<Long, List<ResCoupon>> ucrbsMap = new HashMap<Long, List<ResCoupon>>();
		List<ResCoupon> ucrbs = null;
		for (ResCoupon ucrb : list) {
			if (ucrb.getConditionId() != null) {
				ucrbs = ucrbsMap.get(ucrb.getConditionId());
				if (ucrbs == null) {
					ucrbs = new ArrayList<ResCoupon>();
					ucrbsMap.put(ucrb.getConditionId(), ucrbs);
				}
				ucrbs.add(ucrb);
			}
		}
		if (!ucrbsMap.isEmpty()) {
			this.addCondition(ucrbsMap);

		}
		return list;
	}

	/**
	 * 过滤未达满减条件
	 * 
	 * @param ucrbs
	 * @param totalAmount
	 * @return
	 */
	private List<ResCoupon> parseReduce(List<ResCoupon> ucrbs, Double totalAmount) {
		List<ResCoupon> removeList = new ArrayList<ResCoupon>();
		for (ResCoupon ucrb : ucrbs) {
			if (ucrb.getConditionAmount().doubleValue() > totalAmount) {
				removeList.add(ucrb);
			}
		}
		return removeList;
	}

	/**
	 * 根据订单金额处理券的使用面额
	 * 
	 * @param ucrbs
	 * @param totalAmount
	 */
	private void parseDiscount(List<ResCoupon> ucrbs, Double totalAmount) {
		BigDecimal discountAmount = null;
		for (ResCoupon ucrb : ucrbs) {
			discountAmount = new BigDecimal(((100 - ucrb.getDiscount()) / 100d) * totalAmount);
			discountAmount = discountAmount.setScale(1, BigDecimal.ROUND_DOWN);
			if (ucrb.getFaceAmount().doubleValue() > discountAmount.doubleValue()) {
				ucrb.setFaceAmount(discountAmount);
			}
		}
	}

	/**
	 * 根据使用条件过滤
	 * 
	 * @param ucrbsMap
	 * @param prefectureId
	 * @return
	 */
	private List<ResCoupon> parseCondition(Map<Long, List<ResCoupon>> ucrbsMap, Long prefectureId) {
		List<ResCoupon> removeList = new ArrayList<ResCoupon>();
		Set<Long> keys = ucrbsMap.keySet();
		List<Long> ids = new ArrayList<Long>();
		for (Long key : keys) {
			ids.add(key.longValue());
		}
		List<TemplateCondition> list = this.templateConditionClusterMapper.findTemplateList(ids);
		String json = null;
		ids = new ArrayList<Long>();
		List<TemplateCondition> rccs = new ArrayList<TemplateCondition>();
		for (TemplateCondition cc : list) {
			if (cc.getAvailablePrefecture().shortValue() > 0) {
				json = "," + (String) this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key + cc.getId())
						+ ",";
				if (!json.contains("," + prefectureId + ",")) {
					removeList.addAll(ucrbsMap.get(cc.getId()));
					ucrbsMap.remove(cc.getId());
					rccs.add(cc);
					continue;
				}
			}
			if (cc.getAvailableTime().shortValue() > 0) {
				json = (String) this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key + cc.getId());
				if (cc.getAvailableTime().shortValue() == WEEK_TIME) {
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					if (!wb.check()) {
						removeList.addAll(ucrbsMap.get(cc.getId()));
						ucrbsMap.remove(cc.getId());
						rccs.add(cc);
						continue;
					}
				} else if (cc.getAvailableTime().shortValue() == DAY_TIME) {
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					if (!db.check()) {
						removeList.addAll(ucrbsMap.get(cc.getId()));
						ucrbsMap.remove(cc.getId());
						rccs.add(cc);
						continue;
					}
				}
			}
		}
		Map<Long, TemplateCondition> ccMap = new HashMap<Long, TemplateCondition>();
		Map<Long, ResCondition> crbMap = new HashMap<Long, ResCondition>();
		ResCondition crb = null;
		List<ResCondition> crbs = new ArrayList<ResCondition>();
		list.removeAll(rccs);
		for (TemplateCondition cc : list) {
			ccMap.put(cc.getId(), cc);
			crb = new ResCondition();
			crb.setPreLimit(cc.getAvailablePrefecture().shortValue());
			crb.setTimeLimit(cc.getAvailableTime().shortValue());
			if (cc.getAvailablePrefecture().shortValue() > 0) {
				String redis = (String) this.redisService
						.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key + cc.getId().toString());
				if (StringUtils.isNotBlank(redis)) {
					String[] preids = redis.split(",");
					crb.setPrefectures(preids);
					for (String id : preids) {
						ids.add(Long.valueOf(id));
					}
				}
			}
			if (cc.getAvailableTime().shortValue() > 0) {
				json = (String) this.redisService
						.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key + cc.getId().toString());
				if (cc.getAvailableTime().shortValue() == WEEK_TIME) {
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					crb.setWtb(wb);

				} else if (cc.getAvailableTime().shortValue() == DAY_TIME) {
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					crb.setDtb(db);
				}
			}
			crbMap.put(cc.getId(), crb);
			crbs.add(crb);
		}
		if (ids.size() > 0) {
			List<ResCouponPrefecture> ps = null;
			List<ResPre> rps = this.prefectureClient.prenames(ids);
			if (!CollectionUtils.isEmpty(rps)) {
				ps = new ArrayList<ResCouponPrefecture>();
				ResCouponPrefecture rcp = null;
				for (ResPre rp : rps) {
					rcp = new ResCouponPrefecture();
					rcp.setId(rp.getId());
					rcp.setName(rp.getName());
					ps.add(rcp);
				}
			}
			Map<String, ResCouponPrefecture> pmap = new HashMap<String, ResCouponPrefecture>();
			for (ResCouponPrefecture p : ps) {
				pmap.put(p.getId().toString(), p);
			}
			List<ResCoupon> ucrbs = null;
			ResCouponPrefecture p = null;
			for (Long key : keys) {
				ucrbs = ucrbsMap.get(key);
				crb = crbMap.get(key.longValue());
				for (ResCoupon ucrb : ucrbs) {
					ucrb.setCrb(crb);
					if (crb.getPreLimit() > 0) {
						ucrb.setPreLimit((short) 1);
						if (crb.getPrefectures() != null && crb.getPrefectures().length > 0) {
							p = pmap.get(crb.getPrefectures()[0]);
							if (p != null) {
								ucrb.setPreName(p.getName());
							}
							StringBuffer sb = new StringBuffer();
							for (String pid : crb.getPrefectures()) {
								p = pmap.get(pid);
								if (p != null) {
									sb.append("、");
									sb.append(p.getName());
								}
							}
							if (sb.length() > 0) {
								ucrb.setPreNameList(sb.toString().substring(1));
							}
						}
					}

				}
			}
		}
		return removeList;
	}

	@Override
	public List<ResCoupon> userOrderEnableList(Long userId, Long orderId) {
		List<ResCoupon> list = this.couponClusterMapper.findEnabledList(userId);
		Map<Long, List<ResCoupon>> ucrbsMap = new HashMap<Long, List<ResCoupon>>();
		List<ResCoupon> ucrbs = null;
		for (ResCoupon ucrb : list) {
			if (ucrb.getConditionId() != null) {
				ucrbs = ucrbsMap.get(ucrb.getConditionId());
				if (ucrbs == null) {
					ucrbs = new ArrayList<ResCoupon>();
					ucrbsMap.put(ucrb.getConditionId(), ucrbs);
				}
				ucrbs.add(ucrb);
			}
		}
		ResUserOrder orders = this.orderClient.last(userId);
		Date stopDate = new Date();
		if (orders.getStatus().intValue() == Constants.OrderStatus.SUSPENDED.value) {
			stopDate = orders.getEndTime();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("stallId", orders.getId());
		param.put("plateNo", orders.getPlateNo());
		param.put("startTime", sdf.format(orders.getCreateTime()));
		param.put("endTime", sdf.format(stopDate));
		Map<String, Object> costMap = this.strategyFeeClient.amount(param);
		Double totalAmount = 0d;
		if(costMap != null) {
			String totalAmountStr = costMap.get("chargePrice").toString();
			totalAmount = Double.valueOf(totalAmountStr);
		}
		/*ReqStrategy strategy = new ReqStrategy();
		strategy.setBeginTime(orders.getCreateTime().getTime());
		strategy.setEndTime(stopDate.getTime());
		strategy.setStrategyId(orders.getStrategyId());
		Map<String, Object> costMap = this.strategyBaseClient.fee(strategy);*/
		
		if (ucrbsMap.size() > 0) {
			list.removeAll(parseCondition(ucrbsMap, orders.getPreId()));
		}
		List<ResCoupon> reduceList = new ArrayList<ResCoupon>();
		List<ResCoupon> discountList = new ArrayList<ResCoupon>();
		for (ResCoupon ucrb : list) {
			if (ucrb.getType().shortValue() == CouponType.CONDITION.type) {
				reduceList.add(ucrb);
			} else if (ucrb.getType().shortValue() == CouponType.DISCOUNT.type) {
				discountList.add(ucrb);
			}
		}
		if (reduceList.size() > 0) {
			list.removeAll(this.parseReduce(reduceList, totalAmount));
		}
		if (discountList.size() > 0) {
			this.parseDiscount(discountList, totalAmount);
		}
		return list;
	}

	@Override
	public ResCoupon find(Long id) {
		return this.couponClusterMapper.findById(id);
	}

	@Override
	public void pay(ReqCouponPay rcp) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", CouponStatus.USED.status);
		param.put("updateTime", new Date());
		param.put("id", rcp.getCouponId());
		param.put("orderAmount", rcp.getOrderAmount());
		param.put("usedAmount", rcp.getUsedAmount());
		log.info("param:{}", JsonUtil.toJson(param));
		this.couponMasterMapper.payUpdate(param);

	}

	@Override
	public List<cn.linkmore.coupon.controller.app.response.ResCoupon> usableList(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		List<ResCoupon> list = this.userEnabledList(cu.getId());
		List<cn.linkmore.coupon.controller.app.response.ResCoupon> rcs = new ArrayList<cn.linkmore.coupon.controller.app.response.ResCoupon>();
		cn.linkmore.coupon.controller.app.response.ResCoupon r = null;
		for (cn.linkmore.coupon.response.ResCoupon rc : list) {
			r = new cn.linkmore.coupon.controller.app.response.ResCoupon();
			r.copy(rc);
			rcs.add(r);
		}
		return rcs;
	}

	@Override
	public List<cn.linkmore.coupon.controller.app.response.ResCoupon> paymentList(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		ResUserOrder ruo = this.orderClient.last(cu.getId());
		if (ruo == null || !(ruo.getStatus().intValue() != OrderStatus.UNPAID.value
				|| ruo.getStatus().intValue() != OrderStatus.SUSPENDED.value)) {
			return null;
		}

		List<cn.linkmore.coupon.response.ResCoupon> list = this.userOrderEnableList(cu.getId(), ruo.getId());
		List<cn.linkmore.coupon.controller.app.response.ResCoupon> rcs = new ArrayList<cn.linkmore.coupon.controller.app.response.ResCoupon>();
		cn.linkmore.coupon.controller.app.response.ResCoupon r = null;
		for (cn.linkmore.coupon.response.ResCoupon rc : list) {
			r = new cn.linkmore.coupon.controller.app.response.ResCoupon();
			r.copy(rc);
			rcs.add(r);
		}
		return rcs;
	}

	@Override
	@Transactional
	public boolean send(Long userId) {
		List<ResSubject> list = subjectClusterMapper.findSubjectList();
		ResUser resUser = userClient.findById(userId);
		Integer count = 0;
		if (this.redisService.get(RedisKey.ORDER_SWITCH_STALL_FAILED_COUNT.key + userId.toString()) != null) {
			count = (Integer) this.redisService.get(RedisKey.ORDER_SWITCH_STALL_FAILED_COUNT.key + userId.toString());
		}
		log.info("switch stall fail userId{} count {}", userId, count);
		if (count > 2) {
			return false;
		}
		if (CollectionUtils.isNotEmpty(list) && resUser != null) {
			ResSubject subject = list.get(0);
			ResTemplate temp = this.templateClusterMapper.findById(subject.getTemplateId());
			SendRecord sendRecord = new SendRecord();
			sendRecord.setTemplateId(temp.getId());
			sendRecord.setCreateTime(new Date());
			sendRecord.setType(0);
			sendRecord.setStatus(1);
			sendRecord.setSendTime(new Date());
			sendRecordMasterMapper.save(sendRecord);
			SendUser couponSendUser = new SendUser();
			couponSendUser.setCreateTime(new Date());
			couponSendUser.setUserId(userId);
			couponSendUser.setRecordId(sendRecord.getId());
			couponSendUser.setTemplateId(sendRecord.getTemplateId());
			couponSendUser.setRollbackFlag(0);
			couponSendUser.setCreateTime(new Date());
			sendUserMasterMapper.save(couponSendUser);
			List<ResTemplateItem> items = templateItemClusterMapper.findList(sendRecord.getTemplateId());
			List<Coupon> couponList = new ArrayList<Coupon>();
			Coupon coupon = null;
			for (ResTemplateItem item : items) {
				// 停车券里配置多少张发送多少张
				for (int i = 0; i < item.getQuantity(); i++) {
					coupon = new Coupon();
					coupon.setUserId(couponSendUser.getUserId());
					coupon.setConditionId(sendRecord.getConditionId());
					coupon.setTemplateId(sendRecord.getTemplateId());
					coupon.setRecordId(sendRecord.getId());
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
			couponMasterMapper.insertBatch(couponList);
			// 更新发送用户数量
			temp.setSendQuantity(temp.getSendQuantity() + 1);
			Template template = ObjectUtils.copyObject(temp, new Template());
			this.templateMasterMapper.update(template);
			// 发送短信通知
//			Map<String, String> param = new HashMap<String, String>();
//			param.put("money", temp.getUnitAmount().toString());
//			param.put("enterprise", "凌猫停车");
			ReqSms sms = new ReqSms();
			sms.setMobile(resUser.getUsername());
//			sms.setParam(param);
			sms.setSt(Constants.SmsTemplate.ORDER_CLOSED_NOTICE);
			smsClient.send(sms);
			this.redisService.set(RedisKey.ORDER_SWITCH_STALL_FAILED_COUNT.key + userId.toString(), count + 1,
					ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
		}
		return true;
	}
	
	
	@Override
	@Transactional
	public boolean sendBrandCoupon(Boolean isBrandUser, Long entId, Long userId) {
		boolean flag = false;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDay = sdf.format(date);
        Integer count = 0;
		if (this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay) != null) {
			count = (Integer) this.redisService.get(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay);
			log.info("entId{} count {} " ,entId, count);
		}else {
			log.info("entId {} current day create the key ",entId);
			this.redisService.set(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay, 0);
		}
      
		ResBrandAd brandAd = entBrandAdClient.findByEntId(entId);
		
        //List<ResSubject> list = subjectClusterMapper.findBrandSubjectList();
		ResUser resUser = userClient.findById(userId);
		log.info("brandAd {}, user {} ", JSON.toJSON(brandAd), JSON.toJSON(resUser));
		ResEnterprise enterprise = enterpriseClient.findById(entId);
		log.info("entId {} , ent {} ", entId, JSON.toJSON(enterprise));
		if (brandAd != null && resUser != null && brandAd.getTemplateId() != null) {
			//ResSubject subject = list.get(0);
			ResTemplate temp = this.templateClusterMapper.findById(brandAd.getTemplateId());
			SendRecord sendRecord = new SendRecord();
			sendRecord.setTemplateId(temp.getId());
			sendRecord.setCreateTime(new Date());
			sendRecord.setType(0);
			sendRecord.setStatus(1);
			sendRecord.setSendTime(new Date());
			sendRecordMasterMapper.save(sendRecord);
			SendUser couponSendUser = new SendUser();
			couponSendUser.setCreateTime(new Date());
			couponSendUser.setUserId(userId);
			couponSendUser.setRecordId(sendRecord.getId());
			couponSendUser.setTemplateId(sendRecord.getTemplateId());
			couponSendUser.setRollbackFlag(0);
			couponSendUser.setCreateTime(new Date());
			sendUserMasterMapper.save(couponSendUser);
			List<ResTemplateItem> items = templateItemClusterMapper.findList(sendRecord.getTemplateId());
			List<Coupon> couponList = new ArrayList<Coupon>();
			Coupon coupon = null;
			for (ResTemplateItem item : items) {
				// 停车券里配置多少张发送多少张
				for (int i = 0; i < item.getQuantity(); i++) {
					coupon = new Coupon();
					//设置企业id
					coupon.setEnterpriseId(entId);
					coupon.setUserId(couponSendUser.getUserId());
					coupon.setConditionId(sendRecord.getConditionId());
					coupon.setTemplateId(sendRecord.getTemplateId());
					coupon.setRecordId(sendRecord.getId());
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
			couponMasterMapper.insertBatch(couponList);
			// 更新发送用户数量
			temp.setSendQuantity(temp.getSendQuantity() + 1);
			Template template = ObjectUtils.copyObject(temp, new Template());
			this.templateMasterMapper.update(template);
			// 发送短信通知
			ReqSms sms = new ReqSms();
			
			log.info("enterprise name = {}",enterprise.getName());
			Map<String, String> param = new HashMap<String, String>();
			if(enterprise != null) {
				param.put("brand", enterprise.getName());
			}
			sms.setParam(param);
			sms.setMobile(resUser.getUsername());
			if(isBrandUser) {
				sms.setSt(Constants.SmsTemplate.BRAND_USER_INVITE_NOTICE);
			}else {
				sms.setSt(Constants.SmsTemplate.UN_BRAND_USER_INVITE_NOTICE);
			}
			smsClient.send(sms);
			this.redisService.set(RedisKey.USER_APP_BRAND_COUPON.key + entId + currentDay, count + 1);
			flag = true;
		}
		return flag;
	}

	@Override
	public List<ResCoupon> findBrandCouponList(Long entId, Long userId) {
		ResBrandAd brandAd = entBrandAdClient.findByEntId(entId);
		log.info("-------------entId = {} ,brandAd = {}", entId, JSON.toJSON(brandAd));
		List<ResCoupon> couponList = new ArrayList<ResCoupon>();
		if (brandAd != null && brandAd.getTemplateId() != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("templateId", brandAd.getTemplateId());
			map.put("enterpriseId", entId);
			couponList = couponClusterMapper.findBrandCouponList(map);
		}
		return couponList;
	}

	@Override
	public List<ResTemplate> findEntTemplateList(Long entId) {
		List<ResTemplate> tempList = templateEnClusterMapper.findEntTemplateList(entId);
		return tempList;
	}

	@Override
	public boolean paySend(Long userId , Integer type) {
		List<ResSubject> list = subjectClusterMapper.findBrandSubjectList(type);
		log.info("pay-------------list = {}",JSON.toJSON(list));
		ResUser resUser = userClient.findById(userId);
		if (CollectionUtils.isNotEmpty(list) && resUser != null) {
			ResSubject subject = list.get(0);
			ResTemplate temp = this.templateClusterMapper.findById(subject.getTemplateId());
			log.info("pay-------------temp = {}",JSON.toJSON(temp));
			//当优惠券启用时可以发送优惠券
			if(temp.getStatus() == 1) {
				SendRecord sendRecord = new SendRecord();
				sendRecord.setTemplateId(temp.getId());
				sendRecord.setCreateTime(new Date());
				sendRecord.setType(0);
				sendRecord.setStatus(1);
				sendRecord.setSendTime(new Date());
				sendRecordMasterMapper.save(sendRecord);
				SendUser couponSendUser = new SendUser();
				couponSendUser.setCreateTime(new Date());
				couponSendUser.setUserId(userId);
				couponSendUser.setRecordId(sendRecord.getId());
				couponSendUser.setTemplateId(sendRecord.getTemplateId());
				couponSendUser.setRollbackFlag(0);
				couponSendUser.setCreateTime(new Date());
				sendUserMasterMapper.save(couponSendUser);
				List<ResTemplateItem> items = templateItemClusterMapper.findList(sendRecord.getTemplateId());
				List<Coupon> couponList = new ArrayList<Coupon>();
				Coupon coupon = null;
				for (ResTemplateItem item : items) {
					// 停车券里配置多少张发送多少张
					for (int i = 0; i < item.getQuantity(); i++) {
						coupon = new Coupon();
						coupon.setUserId(couponSendUser.getUserId());
						coupon.setConditionId(sendRecord.getConditionId());
						coupon.setTemplateId(sendRecord.getTemplateId());
						coupon.setRecordId(sendRecord.getId());
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
				log.info("pay-------------couponList = {}",JSON.toJSON(couponList));
				couponMasterMapper.insertBatch(couponList);
				// 更新发送用户数量
				temp.setSendQuantity(temp.getSendQuantity() + 1);
				Template template = ObjectUtils.copyObject(temp, new Template());
				this.templateMasterMapper.update(template);
				// 发送短信通知
//				Map<String, String> param = new HashMap<String, String>();
//				param.put("money", temp.getUnitAmount().toString());
//				param.put("enterprise", "凌猫停车");
				ReqSms sms = new ReqSms();
				sms.setMobile(resUser.getUsername());
//				sms.setParam(param);
				if(type == 7) {
					sms.setSt(Constants.SmsTemplate.SHARE_COUPON_NOTICE);
				}else if (type == 8){
					sms.setSt(Constants.SmsTemplate.NEW_USER_REG_NOTICE);
					log.info("new user reg notice = {}",JSON.toJSON(sms));
				}
				smsClient.send(sms);
			}
		}
		return true;
	}
}
