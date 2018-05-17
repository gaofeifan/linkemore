package cn.linkmore.coupon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.linkmore.coupon.dao.cluster.CouponClusterMapper;
import cn.linkmore.coupon.dao.cluster.CouponTemplateConditionClusterMapper;
import cn.linkmore.coupon.dao.master.CouponMasterMapper;
import cn.linkmore.coupon.entity.CouponTemplateCondition;
import cn.linkmore.coupon.response.ResCondition;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResCouponPrefecture;
import cn.linkmore.coupon.response.ResDayTime;
import cn.linkmore.coupon.response.ResWeekTime;
import cn.linkmore.coupon.service.CouponService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.util.JsonUtil;
/**
 * Service实现 - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponClusterMapper couponClusterMapper;
	
	@Autowired
	private CouponMasterMapper couponMasterMapper;
	
	private PrefectureClient prefectureClient;
	
	private static final short WEEK_TIME = 1;
	private static final short DAY_TIME = 2;
	
	private  CouponTemplateConditionClusterMapper couponThemplateConditionClusterMapper;
	
	
	private void addCondition(Map<Long,List<ResCoupon>> ucrbsMap){ 
		Set<Long>  keys = ucrbsMap.keySet();
		List<Long> ids = new ArrayList<Long>();
		for(Long key:keys){
			ids.add(key.longValue());
		} 
		List<CouponTemplateCondition> list = this.couponThemplateConditionClusterMapper.findConditionList(ids); 
		Map<Long,CouponTemplateCondition> ccMap = new HashMap<Long,CouponTemplateCondition>();
		String json = null;
		ids = new ArrayList<Long>();
		Map<Long,ResCondition> crbMap = new HashMap<Long,ResCondition>(); 
		ResCondition crb = null;
		List<ResCondition> crbs = new ArrayList<ResCondition>();
		for(CouponTemplateCondition cc:list){
			ccMap.put(cc.getId(), cc);
			crb = new ResCondition();
			crb.setPreLimit(cc.getAvailablePrefecture().shortValue());
			crb.setTimeLimit(cc.getAvailableTime().shortValue()); 
			if(cc.getAvailablePrefecture().shortValue()>0){
				 String redis = null;
				 //redis = this.redisTemplate.opsForValue().get(COUPON_TEMP_CONDITION_PREIDS+cc.getId());  
				 if(StringUtils.isNotBlank(redis)){
					 String[] preids = redis.split(",");
					 crb.setPrefectures(preids);
					 for(String id:preids){
						 ids.add(Long.valueOf(id));
					 }
				 } 
			}
			if(cc.getAvailableTime().shortValue()>0){
				//json = this.redisTemplate.opsForValue().get(COUPON_TEMP_CONDITION_USETIME+cc.getId());
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
//			ps = this.prefectureClient.findById(id);
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
	public List<ResCoupon> enabledList(Long userId){
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
}
