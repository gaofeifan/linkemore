package cn.linkmore.enterprise.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncomes;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlows;
import cn.linkmore.enterprise.controller.ent.response.ResIncome;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.ObjectUtils;
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
	public List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> findPreList(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		List<Long> authStall = this.entStallService.findStaffId(map);
		List<ResPreOrderCount> list = this.orderClient.findPreCountByIds(authStall);
		List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> res = new ArrayList<>();
		for (ResPreOrderCount resPreOrderCount : list) {
			res.add(ObjectUtils.copyObject(resPreOrderCount, new cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount()));
		}
		return res;
		
	}
	@Override
	public BigDecimal findPreDayIncome(Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		return this.orderClient.findPreDayIncome(authStall);
	}
	
	@Override
	public cn.linkmore.enterprise.controller.ent.response.ResIncomeList findProceeds(Short type,Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		Map<String, Object> proceeds = this.orderClient.findProceeds(param);
		cn.linkmore.enterprise.controller.ent.response.ResIncomeList income = new cn.linkmore.enterprise.controller.ent.response.ResIncomeList();
		Object object = proceeds.get("amount");
		if(income != null) {
			income.setTotalAmount(new BigDecimal(object.toString()));
		}
		List<ResIncome> incomeLst = null;
		if(proceeds.get("list") != null) {
			List<Object> list = (List<Object>) proceeds.get("list");
			incomeLst = new ArrayList<>();
			for (Object  object2: list) {
				ResIncome i = new ResIncome();
				Map<String,Object> result = (Map<String, Object>)object2 ;
				i.setDayAmount(new BigDecimal(result.get("dayAmount").toString()));
//				Date date = DateUtils.format(result.get("dayTime").toString());
				i.setDayTime(new Date());
				incomeLst.add(i);
			}
			income.setIncomes(incomeLst);
		}
		
		return income;
				
	}
	
	@Override
	public cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow findTrafficFlow(Short type,Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);		
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		Map<String, Object> flow = this.orderClient.findTrafficFlow(param);
		cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow tf = new cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow();
		if(flow.get("number") != null) {
			tf.setTotalNumber(Integer.decode(flow.get("number").toString()));
		}
		if(flow.get("list") != null) {
			List<cn.linkmore.enterprise.controller.ent.response.ResTrafficFlowList> tfs = new ArrayList<>();
			List<Object> list =(List<Object>) flow.get("list");
			for (Object resPreDataList : list) {
				Map<String,Object> result = (Map<String, Object>)resPreDataList ;
				cn.linkmore.enterprise.controller.ent.response.ResTrafficFlowList rtf = new cn.linkmore.enterprise.controller.ent.response.ResTrafficFlowList();
				rtf.setDayNumber(Integer.decode(result.get("dayNumber").toString()));
				rtf.setDayTime(new Date());
				tfs.add(rtf);
			}
			tf.setTfs(tfs);
		}
		
		return tf;
	}
	@Override
	public List<ResChargeList> findChargeDetail(Short type, Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		List<ResChargeList> list = this.orderClient.findChargeDetail(param);
		return list;
	}
	@Override
	public List<ResDayTrafficFlow> findTrafficFlowList(Short type, Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		List<ResTrafficFlow> flowList = this.orderClient.findTrafficFlowList(param);
		List<ResDayTrafficFlow> dayTFs = new ArrayList<>();
		ResDayTrafficFlow dayTF = null;
		List<ResDayTrafficFlows> flows = null;
		for (ResTrafficFlow resTrafficFlow : flowList) {
			dayTF = new ResDayTrafficFlow();
			dayTF.setCarMonthTotal(resTrafficFlow.getCarMonthTotal());
			dayTF.setTime(resTrafficFlow.getTime());
			flows = new ArrayList<>();
			for (ResTrafficFlowList tf : resTrafficFlow.getTrafficFlows()) {
				flows.add(ObjectUtils.copyObject(tf, new ResDayTrafficFlows()));
			}
			dayTF.setTrafficFlows(flows);
			dayTFs.add(dayTF);
		}
		return dayTFs;
	}
	
	@Override
	public List<ResDayIncome> findIncomeList(Short type, Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		List<ResDayIncome> incomes = new ArrayList<>();
		ResDayIncome income = null;
		List<ResDayIncomes> incomeLists = new ArrayList<>();
		List<cn.linkmore.order.response.ResIncome> oIncomes = this.orderClient.findIncomeList(param);
		for (cn.linkmore.order.response.ResIncome resIncome : oIncomes) {
			income = new ResDayIncome();
			income.setDate(resIncome.getDate());
			income.setMonthAmount(resIncome.getMonthAmount());
			for (ResIncomeList incomeList : resIncome.getList()) {
				incomeLists.add(ObjectUtils.copyObject(incomeList, new ResDayIncomes()));
			}
			income.setList(incomeLists);
			incomes.add(income);
		}
		return incomes;
	}
	
	private CacheUser getUser(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		return (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
	}

}
