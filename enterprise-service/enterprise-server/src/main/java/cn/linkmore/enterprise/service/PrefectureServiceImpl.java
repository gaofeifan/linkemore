package cn.linkmore.enterprise.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.ent.response.ResIncome;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.TokenUtil;

/**
 * @author   GFF
 * @Date     2018年7月21日
 * @Version  v2.0
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {

	@Resource
	private RedisService redisService;
	@Resource
	private EntStallService entStallService; 
	@Resource
	private PrefectureClient prefectureClient;
	@Resource
	private OrderClient orderClient;
	@Override
	public List<ResPreOrderCount> findPreList(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		List<Long> authStall = this.entStallService.findStaffId(map);
		List<ResPreOrderCount> list = this.orderClient.findPreCountByIds(authStall);
		return list;
		
	}
	@Override
	public BigDecimal findPreDayIncome(Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		return this.orderClient.findPreDayIncome(authStall);
	}
	
	@Override
	public BigDecimal findProceeds(Short type,Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		Date date = null;
		if(type == 0) {
			date = DateUtils.getPast2Date(-7);
		}else if(type == 1) {
			date = DateUtils.getPast2Date(-15);
		}else if(type == 2) {
			date = DateUtils.getPast2Date(-30);
		}
		param.put("startTime", date);
		param.put("stallIds", authStall);
		return this.orderClient.findProceeds(param);
	}
	
	@Override
	public Integer findTrafficFlow(Short type,Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		Date date = null;
		if(type == 0) {
			date = DateUtils.getPast2Date(-7);
		}else if(type == 1) {
			date = DateUtils.getPast2Date(-15);
		}else if(type == 2) {
			date = DateUtils.getPast2Date(-30);
		}
		param.put("startTime", date);
		param.put("stallIds", authStall);
		return this.orderClient.findTrafficFlow(param);
	}
	@Override
	public List<ResChargeList> findChargeDetail(Short type, Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		Date date = null;
		if(type == 0) {
			date = DateUtils.getPast2Date(-7);
		}else if(type == 1) {
			date = DateUtils.getPast2Date(-15);
		}else if(type == 2) {
			date = DateUtils.getPast2Date(-30);
		}
		param.put("startTime", date);
		param.put("stallIds", authStall);
		return this.orderClient.findChargeDetail(param);
	}
	@Override
	public List<ResTrafficFlow> findTrafficFlowList(Short type, Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		Date date = null;
		if(type == 0) {
			date = DateUtils.getPast2Date(-7);
		}else if(type == 1) {
			date = DateUtils.getPast2Date(-15);
		}else if(type == 2) {
			date = DateUtils.getPast2Date(-30);
		}
		param.put("startTime", date);
		param.put("stallIds", authStall);
		return this.orderClient.findTrafficFlowList(param);
	}
	@Override
	public List<cn.linkmore.order.response.ResIncome> findIncomeList(Short type, Long preId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		Date date = null;
		if(type == 0) {
			date = DateUtils.getPast2Date(-7);
		}else if(type == 1) {
			date = DateUtils.getPast2Date(-15);
		}else if(type == 2) {
			date = DateUtils.getPast2Date(-30);
		}
		param.put("startTime", date);
		param.put("stallIds", authStall);
		return this.orderClient.findIncomeList(param);
	}
	
	

}
