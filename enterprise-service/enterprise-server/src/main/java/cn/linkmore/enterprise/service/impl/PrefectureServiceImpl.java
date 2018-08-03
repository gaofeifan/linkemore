package cn.linkmore.enterprise.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.ent.response.ResCharge;
import cn.linkmore.enterprise.controller.ent.response.ResChargeDetail;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncomes;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlows;
import cn.linkmore.enterprise.controller.ent.response.ResIncome;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.order.client.EntOrderClient;
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
	private EntOrderClient orderClient;
	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> findPreList(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		List<Long> authStall = this.entStallService.findStaffId(map);
		List<ResPreOrderCount> list = this.orderClient.findPreCountByIds(authStall);
		List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> res = new ArrayList<>();
		if(list == null) {
			return res;
		}
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
		if(proceeds == null) {
			return income;
		}
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
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.decode(result.get("dayTime").toString()));
				i.setDayTime(date.getTime());
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
		if(flow == null || flow.size() == 0) {
			return tf;
		}
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
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.decode(result.get("dayTime").toString()));
				rtf.setDayTime(date.getTime());
				tfs.add(rtf);
			}
			tf.setTfs(tfs);
		}
		
		return tf;
	}
	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResChargeList> findChargeDetail(Short type, Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		List<ResChargeList> list = this.orderClient.findChargeDetail(param);
		List<cn.linkmore.enterprise.controller.ent.response.ResChargeList> resList = new ArrayList<>();
		cn.linkmore.enterprise.controller.ent.response.ResChargeList chargeList = null;
		List<ResCharge> charges =null;
		List<ResChargeDetail> chargeDetail = null;
		ResCharge cha = null;
		if(list == null) {
			return resList;
		}
		for (ResChargeList resChargeList : list) {
			charges = new ArrayList<>();
			chargeList = new cn.linkmore.enterprise.controller.ent.response.ResChargeList();
			chargeList.setTodayIncome(resChargeList.getTodayIncome());
			for (cn.linkmore.order.response.ResCharge resCharge : resChargeList.getDetails()) {
				cha = new ResCharge();
				cha.setDate(resCharge.getDate());
				chargeDetail = new ArrayList<>();
				for (cn.linkmore.order.response.ResChargeDetail resChargeDetail : resCharge.getCharge()) {
					chargeDetail.add(ObjectUtils.copyObject(resChargeDetail, new ResChargeDetail() ));
				}
				cha.setCharge(chargeDetail);
				charges.add(cha);
			}
			chargeList.setDetails(charges);
			resList.add(chargeList);
		}
		return resList;
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
		if(oIncomes == null) {
			return incomes;
		}
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
	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResCharge> findChargeDetailNew(Short type,
			Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
		List<Long> authStall = this.entStallService.findStaffId(map);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("stallIds", authStall);
		List<cn.linkmore.order.response.ResCharge> list = this.orderClient.findChargeDetailNew(param);
		List<ResCharge> charges = new ArrayList<>();
		if(list == null) {
			return charges;
		}
		List<ResChargeDetail> chargeDetail = null;
		ResCharge cha = null;
		for (cn.linkmore.order.response.ResCharge resCharge : list) {
			cha = new ResCharge();
			cha.setDate(resCharge.getDate());
			chargeDetail = new ArrayList<>();
			for (cn.linkmore.order.response.ResChargeDetail resChargeDetail : resCharge.getCharge()) {
				chargeDetail.add(ObjectUtils.copyObject(resChargeDetail, new ResChargeDetail() ));
			}
			cha.setCharge(chargeDetail);
			charges.add(cha);
		}
		return charges;
	}

	
}
