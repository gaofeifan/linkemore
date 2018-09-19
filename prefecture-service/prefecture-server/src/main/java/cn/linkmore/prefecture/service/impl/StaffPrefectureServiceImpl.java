package cn.linkmore.prefecture.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.client.StaffOrderClient;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.prefecture.controller.staff.request.ReqPreType;
import cn.linkmore.prefecture.controller.staff.request.ReqPreTypePage;
import cn.linkmore.prefecture.controller.staff.response.ResAmountReport;
import cn.linkmore.prefecture.controller.staff.response.ResAmountReportList;
import cn.linkmore.prefecture.controller.staff.response.ResCarReport;
import cn.linkmore.prefecture.controller.staff.response.ResCarReportList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreListCount;
import cn.linkmore.prefecture.dao.cluster.AdminAuthPreClusterMapper;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.service.AdminAuthService;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StaffPrefectureService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;

/**
 * @author   GFF
 * @Date     2018年9月18日
 * @Version  v2.0
 */
@Service
public class StaffPrefectureServiceImpl implements StaffPrefectureService {
	@Resource
	private RedisService redisService;
	@Resource
	private StaffOrderClient staffOrderClient;
	@Resource
	private StallService stallService;
	@Resource
	private AdminAuthPreClusterMapper adminAuthPreClusterMapper;
	@Resource
	private PrefectureService prefectureService;
	@Resource
	private AdminAuthService adminAuthService;
	
	@Override
	public List<ResStaffPreListCount> findPreList(HttpServletRequest request, Long cityId) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffCityAuth(cu.getId(), cityId)) {
			throw new BusinessException(StatusEnum.STAFF_CITY_EXISTS);
		}
		List<ResStaffPreListCount> preListCounts = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<AdminAuthPre> pres = this.adminAuthPreClusterMapper.findList(map);
		if(pres == null) {
			return preListCounts;
		}
		List<Long> list = pres.stream().map(pre -> pre.getPreId()).collect(Collectors.toList());
		map.put("preIds", list);
		map.put("cityId", cityId);
		List<ResPre> pre = this.prefectureService.findPreByIds(map);
		if(pre == null) {
			return preListCounts;
		}
		list = pre.stream().map(p -> p.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("preIds", list);
		map.put("type", 0);
		List<ResStall> stallIds = this.stallService.findStallsByPreIds(map);
		list = stallIds.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", list);
		List<ResPreOrderCount> preListCount = this.staffOrderClient.findPreListCount(map);
		ResStaffPreListCount staffPre = null;
		for (ResPre resPre : pre) {
			staffPre = new ResStaffPreListCount();
			staffPre.setPreId(resPre.getId());
			staffPre.setPreName(resPre.getName());
			int dayOrder = 0;
			BigDecimal dayAmount = new BigDecimal(0);
			for (ResPreOrderCount resPreOrderCount : preListCount) {
				if(resPre.getId().equals(resPreOrderCount.getPreId()) && list.contains(resPreOrderCount.getStallId())) {
					dayOrder ++;
					dayAmount = dayAmount.add(resPreOrderCount.getDayAmount());
				}
			}
			staffPre.setDayAmount(dayAmount);
			staffPre.setDayOrder(dayOrder);
			preListCounts.add(staffPre);
		}
		return preListCounts;
	}

	@Override
	public BigDecimal findDayIncome(HttpServletRequest request, Long preId) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), preId)) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("preIds", Arrays.asList(preId));
		map.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(map );
		List<Long> list = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", list);
		return this.staffOrderClient.findDayIncome(map);
	}

	@Override
	public BigDecimal findAmountReportCount(HttpServletRequest request, ReqPreType type) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), type.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("preIds", Arrays.asList(type.getPreId()));
		map.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(map);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		return this.staffOrderClient.findAmountReportCount(map);
	}

	@Override
	public Integer findCarReportCount(HttpServletRequest request, ReqPreType type) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), type.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("preIds", Arrays.asList(type.getPreId()));
		map.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(map);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		return this.staffOrderClient.findCarReportCount(map);
	}

	@Override
	public ResAmountReport findAmountReportList(HttpServletRequest request, ReqPreType type) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), type.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("preIds", Arrays.asList(type.getPreId()));
		map.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(map);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		map = this.staffOrderClient.findAmountReportList(map);
		ResAmountReport amountReport = new ResAmountReport();
		if(map == null) {
			return amountReport;
		}
		Object object = map.get("amount");
		if(object != null) {
			amountReport.setTotalAmount(new BigDecimal(object.toString()));
		}
		List<ResAmountReportList> amountReportLists = null;
		ResAmountReportList amountReportList = null;
		if(map.get("list") != null) {
			List<Object> list = (List<Object>) map.get("list");
			amountReportLists = new ArrayList<>();
			for (Object  object2: list) {
				amountReportList = new ResAmountReportList();
				Map<String,Object> result = (Map<String, Object>)object2 ;
				amountReportList.setDayAmount(new BigDecimal(result.get("dayAmount").toString()));
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.decode(result.get("dayTime").toString()));
				amountReportList.setDayTime(date.getTime());
				amountReportLists.add(amountReportList);
			}
			amountReport.setIncomes(amountReportLists);
		}
		return amountReport;
	}

	@Override
	public ResCarReport findCarReportList(HttpServletRequest request, ReqPreType type) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), type.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("preIds", Arrays.asList(type.getPreId()));
		map.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(map);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		map = this.staffOrderClient.findCarReportList(map);
		ResCarReport carReport = new ResCarReport();
		if(map == null) {
			return carReport;
		}
		Object object = map.get("number");
		if(object != null) {
			carReport.setTotalNumber(Integer.decode(object.toString()));
		}
		List<ResCarReportList> carReportLists = null;
		ResCarReportList carReportList = null;
		if(map.get("list") != null) {
			List<Object> list = (List<Object>) map.get("list");
			carReportLists = new ArrayList<>();
			for (Object  object2: list) {
				carReportList = new ResCarReportList();
				Map<String,Object> result = (Map<String, Object>)object2 ;
				carReportList.setDayNumber(Integer.decode(result.get("dayNumber").toString()));
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.decode(result.get("dayTime").toString()));
				carReportList.setDayTime(date.getTime());
				carReportLists.add(carReportList);
			}
			carReport.setTfs(carReportLists);
		}
		return carReport;
	}

	@Override
	public List<ResTrafficFlow> findCarMonthList(HttpServletRequest request, ReqPreTypePage page) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), page.getPreId())) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("preId", page.getPreId()); 
		param.put("now", page.getNow());
		List<ResTrafficFlow> flowList = this.staffOrderClient.findCarMonthList(param);
		List<cn.linkmore.prefecture.controller.staff.response.ResTrafficFlow> dayTFs = new ArrayList<>();
		if(flowList == null) {
			return null;
		}
		cn.linkmore.prefecture.controller.staff.response.ResTrafficFlow dayTF = null;
		List<cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlows> flows = null;
		/*for (ResTrafficFlow resTrafficFlow : flowList) {
			dayTF = new cn.linkmore.prefecture.controller.staff.response.ResTrafficFlow();
			dayTF.setCarMonthTotal(resTrafficFlow.getCarMonthTotal());
			dayTF.sett
			dayTF.setTime(resTrafficFlow.getTime());
			flows = new ArrayList<>();
			for (ResTrafficFlowList tf : resTrafficFlow.getTrafficFlows()) {
				flows.add(ObjectUtils.copyObject(tf, new cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlows()));
			}
			dayTF.setTrafficFlows(flows);
			dayTFs.add(dayTF);
		}*/
		return null;
	}

	@Override
	public List<ResIncome> findAmountMonthList(HttpServletRequest request, ReqPreTypePage page) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), page.getPreId())) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("preId", page.getPreId());
		param.put("now", page.getNow());
		/*List<ResDayIncome> incomes = new ArrayList<>();
		ResDayIncome income = null;
//		List<ResDayIncomes> incomeLists = new ArrayList<>();
		List<cn.linkmore.order.response.ResIncome> oIncomes = this.orderClient.findIncomeList(param);
		if(oIncomes == null) {
			return incomes;
		}
		List<ResDayIncomes> lists = null;
		for (cn.linkmore.order.response.ResIncome resIncome : oIncomes) {
			income = new ResDayIncome();
			income.setDate(resIncome.getDate());
			income.setMonthAmount(resIncome.getMonthAmount());
			lists = new ArrayList<>();
			for (ResIncomeList in : resIncome.getList()) {
				lists.add(ObjectUtils.copyObject(in, new ResDayIncomes()));
			}
			income.setList(lists);
			incomes.add(income);
		}*/
		return null;
	}
	
	
	

	
}
