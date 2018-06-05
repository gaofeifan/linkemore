package cn.linkmore.coupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.CouponStatus;
import cn.linkmore.bean.common.Constants.CouponType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.coupon.dao.cluster.CouponClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateConditionClusterMapper;
import cn.linkmore.coupon.dao.master.CouponMasterMapper;
import cn.linkmore.coupon.entity.TemplateCondition;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCondition;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResCouponPrefecture;
import cn.linkmore.coupon.response.ResDayTime;
import cn.linkmore.coupon.response.ResWeekTime;
import cn.linkmore.coupon.service.CouponService;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
/**
 * Service实现 - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponClusterMapper couponClusterMapper;
	
	@Autowired
	private CouponMasterMapper couponMasterMapper;
	
	@Autowired
	private TemplateConditionClusterMapper templateConditionClusterMapper;
	
	@Autowired
	private PrefectureClient prefectureClient;
	
	@Autowired
	private StrategyBaseClient strategyBaseClient;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private OrderClient orderClient;
	
	private static final short WEEK_TIME = 1;
	private static final short DAY_TIME = 2;
	
	private  TemplateConditionClusterMapper couponThemplateConditionClusterMapper;
	
	
	private void addCondition(Map<Long,List<ResCoupon>> ucrbsMap){ 
		Set<Long>  keys = ucrbsMap.keySet();
		List<Long> ids = new ArrayList<Long>();
		for(Long key:keys){
			ids.add(key.longValue());
		} 
		List<TemplateCondition> list = this.couponThemplateConditionClusterMapper.findTemplateList(ids); 
		Map<Long,TemplateCondition> ccMap = new HashMap<Long,TemplateCondition>();
		String json = null;
		ids = new ArrayList<Long>();
		Map<Long,ResCondition> crbMap = new HashMap<Long,ResCondition>(); 
		ResCondition crb = null;
		List<ResCondition> crbs = new ArrayList<ResCondition>();
		for(TemplateCondition cc:list){
			ccMap.put(cc.getId(), cc);
			crb = new ResCondition();
			crb.setPreLimit(cc.getAvailablePrefecture().shortValue());
			crb.setTimeLimit(cc.getAvailableTime().shortValue()); 
			if(cc.getAvailablePrefecture().shortValue()>0){
				 String redis = null;
				 redis = (String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key+cc.getId().toString());  
				 if(StringUtils.isNotBlank(redis)){
					 String[] preids = redis.split(",");
					 crb.setPrefectures(preids);
					 for(String id:preids){
						 ids.add(Long.valueOf(id));
					 }
				 } 
			}
			if(cc.getAvailableTime().shortValue()>0){ 
				json = (String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key+cc.getId().toString());
				if(cc.getAvailableTime().shortValue()==WEEK_TIME){
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					crb.setWtb(wb);
					
				}else if(cc.getAvailableTime().shortValue()==DAY_TIME){
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					crb.setDtb(db);
				}
			} 
			crbMap.put(cc.getId(), crb);
			crbs.add(crb);
		}  
		if(ids.size()>0){ 
			List<ResCouponPrefecture> ps = null;
			List<ResPre> rps = this.prefectureClient.prenames(ids);
			if(!CollectionUtils.isEmpty(rps)) {
				ps = new ArrayList<ResCouponPrefecture>();
				ResCouponPrefecture rcp = null;
				for(ResPre rp:rps) {
					rcp = new ResCouponPrefecture();
					rcp.setId(rp.getId());
					rcp.setName(rp.getName());
					ps.add(rcp);
				}
			} 
			Map<String,ResCouponPrefecture> pmap = new HashMap<String,ResCouponPrefecture>();
			for(ResCouponPrefecture p:ps){
				pmap.put(p.getId().toString(), p);
			}
			List<ResCoupon> ucrbs = null; 
			ResCouponPrefecture p = null;
			for(Long key:keys){
				ucrbs = ucrbsMap.get(key);
				crb = crbMap.get(key.longValue()); 
				for(ResCoupon ucrb:ucrbs){
					ucrb.setCrb(crb);
					if(crb.getPreLimit()>0){
						ucrb.setPreLimit((short)1);
						if(crb.getPrefectures()!=null&&crb.getPrefectures().length>0){ 
							p = pmap.get(crb.getPrefectures()[0]);
							if(p!=null){
								ucrb.setPreName(p.getName());
							}
							StringBuffer sb = new StringBuffer();
							for(String pid:crb.getPrefectures()){
								p = pmap.get(pid);
								if(p!=null){
									sb.append("、");
									sb.append(p.getName());
								} 
							}
							if(sb.length()>0){
								ucrb.setPreNameList(sb.toString().substring(1));
							}
						}
					}
					
				}
			}
		}
		
	}
	
	@Override
	public List<ResCoupon> userEnabledList(Long userId){
		List<ResCoupon> list = this.couponClusterMapper.findEnabledList(userId);
		Map<Long,List<ResCoupon>> ucrbsMap = new HashMap<Long,List<ResCoupon>>();
		List<ResCoupon> ucrbs = null;
		for(ResCoupon ucrb:list){
			if(ucrb.getConditionId()!=null){
				ucrbs = ucrbsMap.get(ucrb.getConditionId());
				if(ucrbs==null){
					ucrbs = new ArrayList<ResCoupon>();
					ucrbsMap.put(ucrb.getConditionId(), ucrbs);
				}
				ucrbs.add(ucrb);
			} 
		} 
		if(!ucrbsMap.isEmpty()){
			this.addCondition(ucrbsMap);
		} 
		return list;
	}
	
	
	/**
	 * 过滤未达满减条件
	 * @param ucrbs
	 * @param totalAmount
	 * @return
	 */
	private List<ResCoupon> parseReduce(List<ResCoupon> ucrbs,Double totalAmount){
		List<ResCoupon> removeList = new ArrayList<ResCoupon>(); 
		for(ResCoupon ucrb:ucrbs){
			if(ucrb.getConditionAmount().doubleValue()>totalAmount){
				removeList.add(ucrb);
			}
		} 
		return removeList;
	}
	
	/**
	 * 根据订单金额处理券的使用面额
	 * @param ucrbs
	 * @param totalAmount
	 */
	private void parseDiscount(List<ResCoupon> ucrbs,Double totalAmount){ 
		BigDecimal discountAmount = null;
		for(ResCoupon ucrb:ucrbs){ 
			discountAmount = new BigDecimal(((100-ucrb.getDiscount())/100d)*totalAmount);
			discountAmount = discountAmount.setScale(1,BigDecimal.ROUND_DOWN); 
			if(ucrb.getFaceAmount().doubleValue()>discountAmount.doubleValue()){
				ucrb.setFaceAmount(discountAmount);
			}
		} 
	}
	
	/**
	 * 根据使用条件过滤
	 * @param ucrbsMap
	 * @param prefectureId
	 * @return
	 */
	private List<ResCoupon> parseCondition(Map<Long,List<ResCoupon>> ucrbsMap,Long prefectureId){
		List<ResCoupon> removeList = new ArrayList<ResCoupon>();
		Set<Long>  keys = ucrbsMap.keySet();
		List<Long> ids = new ArrayList<Long>();
		for(Long key:keys){
			ids.add(key.longValue());
		}
		List<TemplateCondition> list = this.templateConditionClusterMapper.findTemplateList(ids); 
		String json = null;
		ids = new ArrayList<Long>();
		List<TemplateCondition> rccs = new ArrayList<TemplateCondition>();
		for(TemplateCondition cc:list){ 
			if(cc.getAvailablePrefecture().shortValue()>0){
				json =  ","+(String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key+cc.getId())+",";
				if(!json.contains(","+prefectureId+",")){
					removeList.addAll(ucrbsMap.get(cc.getId()));
					ucrbsMap.remove(cc.getId());
					rccs.add(cc);
					continue;
				}
			}
			if(cc.getAvailableTime().shortValue()>0){
				json = (String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key+cc.getId());
				if(cc.getAvailableTime().shortValue()==WEEK_TIME){ 
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					if(!wb.check()){
						removeList.addAll(ucrbsMap.get(cc.getId()));
						ucrbsMap.remove(cc.getId());
						rccs.add(cc);
						continue;
					}
				}else if(cc.getAvailableTime().shortValue()==DAY_TIME){
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					if(!db.check()){
						removeList.addAll(ucrbsMap.get(cc.getId()));
						ucrbsMap.remove(cc.getId());
						rccs.add(cc);
						continue;
					}
				}
			} 
		}
		Map<Long,TemplateCondition> ccMap = new HashMap<Long,TemplateCondition>();
		Map<Long,ResCondition> crbMap = new HashMap<Long,ResCondition>(); 
		ResCondition crb = null;
		List<ResCondition> crbs = new ArrayList<ResCondition>();
		list.removeAll(rccs);
		for(TemplateCondition cc:list){
			ccMap.put(cc.getId(), cc);
			crb = new ResCondition();
			crb.setPreLimit(cc.getAvailablePrefecture().shortValue());
			crb.setTimeLimit(cc.getAvailableTime().shortValue()); 
			if(cc.getAvailablePrefecture().shortValue()>0){ 
				 String redis = (String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key+cc.getId().toString());
				 if(StringUtils.isNotBlank(redis)){
					 String[] preids = redis.split(",");
					 crb.setPrefectures(preids);
					 for(String id:preids){
						 ids.add(Long.valueOf(id));
					 }
				 } 
			}
			if(cc.getAvailableTime().shortValue()>0){
				json = (String)this.redisService.get(RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key+cc.getId().toString());
				if(cc.getAvailableTime().shortValue()==WEEK_TIME){
					ResWeekTime wb = JsonUtil.toObject(json, ResWeekTime.class);
					crb.setWtb(wb);
					
				}else if(cc.getAvailableTime().shortValue()==DAY_TIME){
					ResDayTime db = JsonUtil.toObject(json, ResDayTime.class);
					crb.setDtb(db);
				}
			} 
			crbMap.put(cc.getId(), crb);
			crbs.add(crb);
		}  
		if(ids.size()>0){ 
			List<ResCouponPrefecture> ps = null;
			List<ResPre> rps = this.prefectureClient.prenames(ids);
			if(!CollectionUtils.isEmpty(rps)) {
				ps = new ArrayList<ResCouponPrefecture>();
				ResCouponPrefecture rcp = null;
				for(ResPre rp:rps) {
					rcp = new ResCouponPrefecture();
					rcp.setId(rp.getId());
					rcp.setName(rp.getName());
					ps.add(rcp);
				}
			} 
			Map<String,ResCouponPrefecture> pmap = new HashMap<String,ResCouponPrefecture>();
			for(ResCouponPrefecture p:ps){
				pmap.put(p.getId().toString(), p);
			}
			List<ResCoupon> ucrbs = null; 
			ResCouponPrefecture p = null;
			for(Long key:keys){
				ucrbs = ucrbsMap.get(key);
				crb = crbMap.get(key.longValue()); 
				for(ResCoupon ucrb:ucrbs){
					ucrb.setCrb(crb);
					if(crb.getPreLimit()>0){
						ucrb.setPreLimit((short)1);
						if(crb.getPrefectures()!=null&&crb.getPrefectures().length>0){ 
							p = pmap.get(crb.getPrefectures()[0]);
							if(p!=null){
								ucrb.setPreName(p.getName());
							}
							StringBuffer sb = new StringBuffer();
							for(String pid:crb.getPrefectures()){
								p = pmap.get(pid);
								if(p!=null){
									sb.append("、");
									sb.append(p.getName());
								} 
							}
							if(sb.length()>0){
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
		Map<Long,List<ResCoupon>> ucrbsMap = new HashMap<Long,List<ResCoupon>>();
		List<ResCoupon> ucrbs = null;
		for(ResCoupon ucrb:list){
			if(ucrb.getConditionId()!=null){
				ucrbs = ucrbsMap.get(ucrb.getConditionId());
				if(ucrbs==null){
					ucrbs = new ArrayList<ResCoupon>();
					ucrbsMap.put(ucrb.getConditionId(), ucrbs);
				}
				ucrbs.add(ucrb);
			} 
		}   
		ResUserOrder orders = this.orderClient.last(userId);  
		Date stopDate = new Date();
		if(orders.getStatus().intValue() == Constants.OrderStatus.SUSPENDED.value){
			stopDate = orders.getEndTime();
		} 
		ReqStrategy strategy = new ReqStrategy();
		strategy.setBeginTime(orders.getCreateTime().getTime());
		strategy.setEndTime(stopDate.getTime());
		strategy.setStrategyId(orders.getStrategyId());
		Map<String, Object> costMap = this.strategyBaseClient.fee(strategy);
		String totalAmountStr = costMap.get("totalAmount").toString(); 
		Double totalAmount = 0d;
		try{
			totalAmount = Double.valueOf(totalAmountStr);
		}catch(Exception e){
			
		} 
		if(ucrbsMap.size()>0){
			list.removeAll(parseCondition(ucrbsMap,orders.getPreId()));
		} 
		List<ResCoupon> reduceList = new ArrayList<ResCoupon>();
		List<ResCoupon> discountList = new ArrayList<ResCoupon>();
		for(ResCoupon ucrb:list){
			if(ucrb.getType().shortValue()==CouponType.CONDITION.type){
				reduceList.add(ucrb);
			}else if(ucrb.getType().shortValue()==CouponType.DISCOUNT.type){
				discountList.add(ucrb);
			}
		} 
		if(reduceList.size()>0){
			list.removeAll(this.parseReduce(reduceList, totalAmount));
		}
		if(discountList.size()>0){
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
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("status", CouponStatus.USED.status);
		param.put("updateTime", new Date().getTime());
		param.put("id", rcp.getCouponId());
		param.put("orderAmount", rcp.getOrderAmount());
		param.put("usedAmount", rcp.getUsedAmount());
		this.couponMasterMapper.payUpdate(param);
		
	}
}
