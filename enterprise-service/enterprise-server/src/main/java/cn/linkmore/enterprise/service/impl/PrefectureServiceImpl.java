package cn.linkmore.enterprise.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.ent.response.ResChargeDetail;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncomes;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlows;
import cn.linkmore.enterprise.controller.ent.response.ResIncome;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntAuthStall;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.service.EntStaffService;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.order.client.EntOrderClient;
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
	@Resource
	private EntAuthPreClusterMapper authPreClusterMapper;
	@Resource
	private EntStaffService entStaffService;
	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> findPreList(HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
//		List<Long> authStall = this.entStallService.findStaffId(map);
		List<Long> preIds = this.authPreClusterMapper.findPreId(map);
		List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> res = new ArrayList<>();
		if(preIds == null || preIds.size() == 0) {
			return res;
		}
		List<ResPreOrderCount> list = this.orderClient.findPreCountByIds(preIds);
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
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		CacheUser ru = getUser(request);
		Map<String, Long> map = new  HashMap<>();
		map.put("staffId", ru.getId());
		map.put("preId", preId);
//		List<Long> id = this.authPreClusterMapper.findPreId(map);
//		List<Long> authStall = this.entStallService.findStaffId(map);
		return this.orderClient.findPreDayIncome(preId);
	}
	
	@Override
	public cn.linkmore.enterprise.controller.ent.response.ResIncomeList findProceeds(Short type,Long preId, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId);
		Map<String, Object> proceeds = this.orderClient.findProceeds(param);
		cn.linkmore.enterprise.controller.ent.response.ResIncomeList income = new cn.linkmore.enterprise.controller.ent.response.ResIncomeList();
		if(proceeds == null) {
			return income;
		}
		Object object = proceeds.get("amount");
		if(object != null) {
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
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId",preId);
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
	public Integer findTrafficFlowCount(Short type, Long preId, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId);
		Integer count = this.orderClient.findTrafficFlowCount(param);
		return count;
	}
	
	@Override
	public List<ResChargeDetail> findChargeDetail(Integer pageNo,Long preId, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", new Date());
		param.put("preId", preId);
		param.put("pageNo", pageNo);
		List<cn.linkmore.order.response.ResChargeDetail> list = this.orderClient.findChargeDetail(param);
//		List<cn.linkmore.enterprise.controller.ent.response.ResChargeList> resList = new ArrayList<>();
//		cn.linkmore.enterprise.controller.ent.response.ResChargeList chargeList = null;
//		List<ResCharge> charges =null;
		List<ResChargeDetail> chargeDetail = new ArrayList<>();
		for (cn.linkmore.order.response.ResChargeDetail resChargeDetail : list) {
			chargeDetail.add(ObjectUtils.copyObject(resChargeDetail, new ResChargeDetail() ));
		}
		return chargeDetail;
	}
	@Override
	public ResDayTrafficFlow findTrafficFlowList(Integer pageNo,Short type, Long preId,String date, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId); 
		param.put("date", date);
		param.put("pageNo", pageNo);
		ResTrafficFlow flowList = this.orderClient.findTrafficFlowList(param);
//		List<ResDayTrafficFlow> dayTFs = new ArrayList<>();
		ResDayTrafficFlow dayTF = new ResDayTrafficFlow();
		List<ResDayTrafficFlows> flows = null;
//		for (ResTrafficFlow resTrafficFlow : flowList) {
		dayTF.setCarMonthTotal(flowList.getCarMonthTotal());
		dayTF.setTime(flowList.getTime());
		flows = new ArrayList<>();
		for (ResTrafficFlowList tf : flowList.getTrafficFlows()) {
			flows.add(ObjectUtils.copyObject(tf, new ResDayTrafficFlows()));
		}
			dayTF.setTrafficFlows(flows);
//			dayTFs.add(dayTF);
//		}
		return dayTF;
	}
	
	@Override
	public ResDayIncome findIncomeList(Integer pageNo,Short type, Long preId,String date, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		if(!checkAuthPre(request, preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId);
		param.put("date", date);
		param.put("pageNo", pageNo);
//		List<ResDayIncome> incomes = new ArrayList<>();
		ResDayIncome income = new ResDayIncome();
		List<ResDayIncomes> incomeLists = new ArrayList<>();
		cn.linkmore.order.response.ResIncome oIncomes = this.orderClient.findIncomeList(param);
		if(oIncomes == null) {
			return income;
		}
//		for (cn.linkmore.order.response.ResIncome resIncome : oIncomes) {
		income.setDate(oIncomes.getDate());
		income.setMonthAmount(oIncomes.getMonthAmount());
		for (ResIncomeList incomeList : oIncomes.getList()) {
			incomeLists.add(ObjectUtils.copyObject(incomeList, new ResDayIncomes()));
		}
		income.setList(incomeLists);
//		incomes.add(income);
//		}
		return income;
	}
	
	private CacheUser getUser(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		return (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
	}
	
	
/*	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResCharge> findChargeDetailNew(Short type,
			Long preId, HttpServletRequest request) {
		CacheUser ru = getUser(request);
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId);
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
	}*/
	
	@Override
	public BigDecimal findProceedsAmount(Short type, Long preId, HttpServletRequest request) {
		if(!checkAuthStaff(request)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("startTime", type);
		param.put("preId", preId);
		return this.orderClient.findProceedsAmount(param);
	}
	
	public boolean checkAuthPre(HttpServletRequest request,Long preId) {
		CacheUser user = getUser(request);
		Map<String, Object> map = new HashMap<>();
		map.put("staffId", user.getId());
		map.put("preId", preId);
		Integer size = this.authPreClusterMapper.checkAuthPre(map);
		if(size > 0) {
			return true;
		}
		return false;
	}
	public boolean checkAuthStaff(HttpServletRequest request) {
		CacheUser user = getUser(request);
		EntStaff staff = this.entStaffService.findById(user.getId());
		if(staff.getType() == 1) {
			return true;
		}else if(staff.getType() == 0){
			return false;
		}
		return false;
	}
}
