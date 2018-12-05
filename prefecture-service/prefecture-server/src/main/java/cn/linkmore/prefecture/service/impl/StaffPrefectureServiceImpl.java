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
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.prefecture.controller.staff.request.ReqPreType;
import cn.linkmore.prefecture.controller.staff.request.ReqPreTypePage;
import cn.linkmore.prefecture.controller.staff.response.ResAmountDetail;
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
import cn.linkmore.util.ObjectUtils;
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
		map.put("categorys", Arrays.asList(0,1));
		List<ResPre> pre = this.prefectureService.findPreByIds(map);
		if(pre == null || pre.size() == 0) {
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
		if(stalls != null && stalls.size() != 0) {
			return this.staffOrderClient.findDayIncome(map);
		}
		return new BigDecimal(0);
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
		if(stallIds != null && stallIds.size() != 0) {
			return this.staffOrderClient.findAmountReportCount(map);
		}
		return new BigDecimal(0); 
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
		if(stallIds != null && stallIds.size() != 0) {
			return this.staffOrderClient.findCarReportCount(map);
		}
		return 0;
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
		ResAmountReport amountReport = new ResAmountReport();
		if(stallIds == null || stallIds.size() == 0) {
			return amountReport;
		}
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		map = this.staffOrderClient.findAmountReportList(map);
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
		ResCarReport carReport = new ResCarReport();
		if(stallIds == null || stallIds.size() == 0) {
			return carReport; 
		}
		map = new HashMap<>();
		map.put("stallIds", stallIds);
		map.put("type", type.getType());
		map = this.staffOrderClient.findCarReportList(map);
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
	public List<cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlow> findCarMonthList(HttpServletRequest request, ReqPreTypePage page) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), page.getPreId())) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("preIds", Arrays.asList(page.getPreId()));
		param.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(param);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		List<cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlow> dayTFs = new ArrayList<>();
		if(stallIds == null || stallIds.size() == 0) {
			return dayTFs;
		}
		param = new HashMap<>();
		param.put("now", page.getNow());
		param.put("stallIds", stallIds);
		List<ResTrafficFlow> flowList = this.staffOrderClient.findCarMonthList(param);
		if(flowList == null) {
			return dayTFs;
		}
		cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlow dayTF = null;
		List<cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlows> flows = null;
		for (ResTrafficFlow resTrafficFlow : flowList) {
			dayTF = new cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlow();
			dayTF.setCarMonthTotal(resTrafficFlow.getCarMonthTotal());
			dayTF.setTime(resTrafficFlow.getTime());
			flows = new ArrayList<>();
			for (ResTrafficFlowList tf : resTrafficFlow.getTrafficFlows()) {
				flows.add(ObjectUtils.copyObject(tf, new cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlows()));
			}
			dayTF.setTrafficFlows(flows);
			dayTFs.add(dayTF);
		}
		return dayTFs;
	}

	@Override
	public List<cn.linkmore.prefecture.controller.staff.response.ResDayIncome> findAmountMonthList(HttpServletRequest request, ReqPreTypePage page) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), page.getPreId())) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("preIds", Arrays.asList(page.getPreId()));
		param.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(param);
		List<cn.linkmore.order.response.ResIncome> oIncomes = this.staffOrderClient.findAmountMonthList(param);
		List<cn.linkmore.prefecture.controller.staff.response.ResDayIncome> incomes = new ArrayList<>();
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		if(stallIds == null || stallIds.size() ==0) {
			return incomes;
		}
		param = new HashMap<>();
		param.put("now", page.getNow());
		param.put("stallIds", stallIds);
		cn.linkmore.prefecture.controller.staff.response.ResDayIncome income = null;
//		List<ResDayIncomes> incomeLists = new ArrayList<>();
		if(oIncomes == null) {
			return incomes;
		}
		List<cn.linkmore.prefecture.controller.staff.response.ResDayIncomes> lists = null;
		for (cn.linkmore.order.response.ResIncome resIncome : oIncomes) {
			income = new cn.linkmore.prefecture.controller.staff.response.ResDayIncome();
			income.setDate(resIncome.getDate());
			income.setMonthAmount(resIncome.getMonthAmount());
			lists = new ArrayList<>();
			for (ResIncomeList in : resIncome.getList()) {
				lists.add(ObjectUtils.copyObject(in, new cn.linkmore.prefecture.controller.staff.response.ResDayIncomes()));
			}
			income.setList(lists);
			incomes.add(income);
		}
		return incomes;
	}

	@Override
	public List<ResAmountDetail> findAmountDetail(Integer pageNo, Long preId, HttpServletRequest request) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		if(!stallService.checkStaffPreAuth(cu.getId(), preId)) {
			throw new BusinessException(StatusEnum.UNAUTHORIZED);
		}
		Map<String,Object> param = new HashMap<>();
		param.put("preIds", Arrays.asList(preId));
		param.put("type", 0);
		List<ResStall> stalls = this.stallService.findStallsByPreIds(param);
		List<Long> stallIds = stalls.stream().map(s -> s.getId()).collect(Collectors.toList());
		List<ResAmountDetail> chargeDetail = new ArrayList<>();
		if(stallIds == null || stallIds.size() == 0) {
			return chargeDetail;
		}
		param = new HashMap<>();
		param.put("stallIds", stallIds);
		param.put("pageNo", pageNo);
		List<cn.linkmore.order.response.ResChargeDetail> list = this.staffOrderClient.findAmountDetail(param);
		for (cn.linkmore.order.response.ResChargeDetail resChargeDetail : list) {
			chargeDetail.add(ObjectUtils.copyObject(resChargeDetail, new ResAmountDetail() ));
		}
		return chargeDetail;
	}
	
	
	

	
}
