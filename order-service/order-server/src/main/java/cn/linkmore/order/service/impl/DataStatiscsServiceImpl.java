package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;
import cn.linkmore.enterprise.response.ResStaffPreOwnerStall;
import cn.linkmore.order.controller.staff.ReqPreReportForms;
import cn.linkmore.order.controller.staff.request.ReqPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreList;
import cn.linkmore.order.controller.staff.response.ResPreListAndDetails;
import cn.linkmore.order.controller.staff.response.ResPreListType;
import cn.linkmore.order.controller.staff.response.ResPreReportForms;
import cn.linkmore.order.entity.ResStaffDataCountVo;
import cn.linkmore.order.request.ReqDataCount;
import cn.linkmore.order.response.ResPreOrderDetails;
import cn.linkmore.order.response.ResTempStallReportForms;
import cn.linkmore.order.service.DataStatiscsService;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StaffAdminUserClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStaffPreDetails;
import cn.linkmore.prefecture.response.ResStaffPres;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallType;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.StaffUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;

/**
 * @author   GFF
 * @Date     2019年5月14日
 * @Version  v2.0
 */
@Service
public class DataStatiscsServiceImpl implements DataStatiscsService {
    private UserFactory userFactory = StaffUserFactory.getInstance();
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public static Boolean DATA_RISE = false;
	@Resource
	private StallClient stallClient;
	@Resource
	private EntRentedRecordClient entRentedRecordClient;
	@Resource
	private OrdersService ordersService;
	@Resource
	private PrefectureClient prefectureClient;
	@Resource
	private StaffAdminUserClient staffAdminUserClient;
	@Resource
	private RedisService redisService;
	@Override
	public ResPreListAndDetails findPreList(HttpServletRequest request, Long cityId) {
		CacheUser user = getUser(request);
		List<ResPreList> list = new ArrayList<>();
		ResPreList preList = null;
		ResPreListAndDetails preListAndDetails = new ResPreListAndDetails();
		List<ResStaffPres> pres = staffAdminUserClient.findUserPres(user.getId());
		if(getRise()) {
			log.info("展示自定义数据");
			Set<Object> members = this.redisService.members(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key);
			if(members == null || members.size() == 0) {
				return preListAndDetails;
			}
			for (ResStaffPres resStaffPres : pres) {
				for (Object object : members) {
					preList = (ResPreList)object;
					if(preList.getPreId().longValue() == resStaffPres.getPreId().longValue()) {
						list.add(preList);
						break;
					}
				}
			}
			preListAndDetails.setPreLists(list);
			if(list.size() == 1) {
				preListAndDetails.setType((short)1);
				ReqPreDetails de = new ReqPreDetails();
//				de.setFloor(Constants.FLOOR_ALL);
				de.setPreId(list.get(0).getPreId());
				ResPreDetails resPreDetails = this.findPreDetails(request, de);
				if(resPreDetails != null) {
					preListAndDetails.setDetails(resPreDetails);
				}
			}
			return preListAndDetails;
		}else {
			log.info("展示实际数据");
			if(pres == null) {
				log.info("查询用户有权限的车区为null");
				return preListAndDetails;
			}
			log.info("用户车区【"+JsonUtil.toJson(pres)+"】");
			List<Long> preIds = pres.stream().map(p -> p.getPreId()).collect(Collectors.toList());
			Map<String, Object> map = new HashMap<>();
			map.put("preIds", preIds);
			map.put("cityId", cityId);
			List<ResPre> resPres = this.prefectureClient.findPreByIds(map );
			
			List<ResStallType> stallTypes = this.stallClient.findStallType(preIds);
			if(resPres == null ) {
				return preListAndDetails;
			}
			preIds = resPres.stream().map(p -> p.getId()).collect(Collectors.toList());
			if(preIds == null || preIds.size() == 0 ) {
				return preListAndDetails;
			}
			Date startTime = new Date();
			ReqStaffPreOwnerStall details = new ReqStaffPreOwnerStall(preIds,startTime,startTime);
			List<ResStaffOwnerUseStall> preNumbers = entRentedRecordClient.findPreUseNumber(details);
			log.info("固定车位数据【"+JsonUtil.toJson(preNumbers)+"】");
			List<ResStaffPreOwnerStall> orders = ordersService.findPresOrder(details);
			log.info("临停车位数据【"+JsonUtil.toJson(preNumbers)+"】");
			for (ResPre resStaffPres : resPres) {
				preList = new ResPreList();
				preList.setPreId(resStaffPres.getId());
				preList.setPreName(resStaffPres.getName());
				for (ResStaffPres staffPre : pres) {
					if(staffPre.getPreId().longValue() == resStaffPres.getId().doubleValue()) {
						preList.getListType().setType(setType(stallTypes, staffPre.getPreId().longValue()));
						preList.getListType().setFixed(staffPre.getPreType().getOwnerStallNumber());
						for (ResStaffOwnerUseStall resStaffOwnerUseStall : preNumbers) {
							if(resStaffPres.getId().longValue() == resStaffOwnerUseStall.getPreId().longValue()) {
								preList.getListType().setFixedNumberUse(resStaffOwnerUseStall.getUseNumber());
								preList.getListType().setFixed(resStaffOwnerUseStall.getOwnerStallNumber());
								break;
							}
						}
						for (ResStaffPreOwnerStall o : orders) {
							if(staffPre.getPreId().longValue() == o.getPreId().longValue()) {
								preList.getListType().setOrderConunt(o.getOrderNumber());
								preList.getListType().setOrderIncome(o.getOrderAmount());
								break;
							}
						}
						list.add(preList);
						break;
					}
				}
			}
			preListAndDetails.setPreLists(list);
			if(list.size() == 1) {
				preListAndDetails.setType((short)1);
				ReqPreDetails de = new ReqPreDetails();
//				de.setFloor(null);
				de.setPreId(list.get(0).getPreId());
				ResPreDetails resPreDetails = this.findPreDetails(request, de);
				if(resPreDetails != null) {
					preListAndDetails.setDetails(resPreDetails);
				}
			}
			return preListAndDetails;
		}
	}

	@Override
	public ResPreDetails findPreDetails(HttpServletRequest request, ReqPreDetails details) {
		ResStaffPreDetails de = stallClient.findPreStallDetails(details.getPreId(),details.getFloor());
		ResPreDetails preDetails = new ResPreDetails();
		if(de == null) {
			return preDetails;
		}
		List<String> floor = this.prefectureClient.getFloor(details.getPreId());
		if(floor != null) {
			preDetails.setFloors(floor);
		}
		ResPrefectureDetail prefectureDetail = this.prefectureClient.findById(details.getPreId());
		preDetails.setPreId(prefectureDetail.getId());
		preDetails.setPreName(prefectureDetail.getName());
		if(getRise()) {
			Object object = this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key,details.getPreId());
			List<ResStaffDataCountVo<ResPreDetails>> vos = (List<ResStaffDataCountVo<ResPreDetails>>)object;
			if(vos == null || vos.size()==0) {
				return preDetails;
			}
			for (ResStaffDataCountVo<ResPreDetails> resStaffDataCountVo : vos) {
				if(StringUtils.isBlank(resStaffDataCountVo.getFloor())) {
					if(resStaffDataCountVo.getFloor().equals(Constants.FLOOR_ALL)) {
						preDetails = resStaffDataCountVo.getT();
					}
				}else if(resStaffDataCountVo.getFloor().equals(resStaffDataCountVo.getFloor())) {
					preDetails = resStaffDataCountVo.getT();
				}
			}
			return preDetails;
		}
		List<ResStallType> stallType = this.stallClient.findStallType(Arrays.asList(de.getPreId()));
		Map<String, Object> param = new HashMap<>();
		param .put("preId",details.getPreId());
		param.put("floor", details.getFloor());
		List<ResStall> preStallList = this.stallClient.findPreStallList(param );

		if(preStallList == null || preStallList.size() == 0) {
			return preDetails;
		}
		preDetails.setTotalStallNumber(de.getTotalStallNumber());
		preDetails.setEntTotalStallNumber(de.getOwnerStallNumber());
		preDetails.setEntStallUseNumber(de.getOwnerUseStallNumber());
		preDetails.setAppTotalStallNumber(de.getTempStallNumber());
		preDetails.setAppStallUseNumber(de.getTempUseStallNumber());
		preDetails.setStallUseNumber(de.getUseStalNumber());
		preDetails.setType(setType(stallType, de.getPreId()));
		List<Long> stallIds = preStallList.stream().map(s->s.getId()).collect(Collectors.toList());
		ResOwnerStallDetails ownerStall = this.entRentedRecordClient.findPreDetails(new cn.linkmore.enterprise.request.ReqPreDetails(null,stallIds,details.getPreId()));
		if(ownerStall != null) {
			preDetails.setEntAuth(ownerStall.getAuth());
			preDetails.setEntHelpOneself(ownerStall.getHelpOneself());
			preDetails.setEntStallUseCount(ownerStall.getUseCount());
		}
		ResPreOrderDetails orderDetails= this.ordersService.findPreOrderDetails(new cn.linkmore.enterprise.request.ReqPreDetails(null,stallIds,details.getPreId()));
		if(orderDetails != null) {
			preDetails.setAppOrderIncome(orderDetails.getOrderIncome());
			preDetails.setAppOrderCount(orderDetails.getOrderNumber());
			preDetails.setAppOrderOver(orderDetails.getOrderOver());
			preDetails.setAppOrderUnfinished(orderDetails.getOrderNotOver());
		}
		return preDetails;
	}

	@Override
	public ResPreReportForms findPreReportForms(HttpServletRequest request, ReqPreReportForms reportForms) {
		cn.linkmore.enterprise.request.ReqPreDetails details = new cn.linkmore.enterprise.request.ReqPreDetails(reportForms.getFloor(),null,reportForms.getPreId());
		ResPreReportForms forms = new ResPreReportForms();
		List<String> floor = this.prefectureClient.getFloor(details.getPreId());
		if(floor != null) {
			forms.setFloors(floor);
		}
		if(getRise()) {
			Object object = this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key, reportForms.getPreId());
			if(object != null) {
				List<ResStaffDataCountVo<ResPreReportForms>> reportVos = (List<ResStaffDataCountVo<ResPreReportForms>>)object;
				for (ResStaffDataCountVo<ResPreReportForms> resStaffDataCountVo : reportVos) {
					if(StringUtils.isNoneBlank(reportForms.getFloor())) {
						if(reportForms.getFloor().equals(resStaffDataCountVo.getFloor())) {
							if(reportForms.getType() == resStaffDataCountVo.getType().shortValue()) {
								return resStaffDataCountVo.getT();
							}
						}
					}else {
						if(reportForms.getType() == resStaffDataCountVo.getType().shortValue()) {
							return resStaffDataCountVo.getT();
						}
					}
				}
			}
			return forms;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("preId", reportForms.getPreId());
		if(StringUtils.isNotBlank( reportForms.getFloor())) {
			param.put("floor", reportForms.getFloor());
		}
		List<ResStall> preStallList = this.stallClient.findPreStallList(param );

		if(preStallList == null || preStallList.size() == 0) {
			return forms;
		}
		
		ResPrefectureDetail prefectureDetail = this.prefectureClient.findById(details.getPreId());
		forms.setValidTime(prefectureDetail.getCreateTime());
	
		List<ResStallType> stallType = this.stallClient.findStallType(Arrays.asList(details.getPreId()));
		forms.setType(setType(stallType, details.getPreId()));
		int stallLength = 0;
		int stallLengthUp = 0;
		if(reportForms.getType() == 0) {
			Date date = reportForms.getDate();
			details.setStartTime(date);
			details.setEndTime(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			date = DateUtils.getPast2String(-1,calendar);
			details.setContrastStartTime(date);
			details.setContrastEndTime(date);
		}else{
			if(reportForms.getType() == 1) {
				Date[] dates = DateUtils.getWeekDate(reportForms.getDate());
				details.setStartTime(dates[0]);
				details.setEndTime(dates[1]);
				Calendar instance = Calendar.getInstance();
				instance.setTime(dates[0]);
				Date start = DateUtils.getPast2String(-1, instance);
				Date[] datesc = DateUtils.getWeekDate(start);
				details.setContrastStartTime((datesc[0]));
				details.setContrastEndTime(datesc[1]);
				details.setDay(7);
				details.setContrastDay(7);
			}else if(reportForms.getType() == 2) {
				Date[] dates = DateUtils.getMonthDate(reportForms.getDate());
				details.setStartTime(dates[0]);
				details.setEndTime(dates[1]);
				Calendar instance = Calendar.getInstance();
				instance.setTime(dates[0]);
				Date start = DateUtils.getPast2String(-1, instance);
				Date[] datec = DateUtils.getMonthDate(start);
				details.setContrastEndTime(datec[1]);
				details.setContrastStartTime(datec[0]);
				details.setDay(DateUtils.differentDaysByMillisecond(details.getStartTime(), details.getEndTime()));
				details.setContrastDay(DateUtils.differentDaysByMillisecond(details.getContrastStartTime(),details.getContrastEndTime()));
			}
		}
			List<Long> stallIds = preStallList.stream().filter(s -> DateUtils.convert(s.getCreateTime(), null).getTime() <= details.getEndTime().getTime() &&s.getType() == (short)2).map(s -> s.getId() ).collect(Collectors.toList());
			details.setStallIds(stallIds);
			details.setStallCount(stallIds.size());
			stallLength +=stallIds.size();
			stallIds = preStallList.stream().filter(s -> DateUtils.convert(s.getCreateTime(), null).getTime() <= details.getContrastEndTime().getTime()  &&s.getType() == (short)2).map(s -> s.getId() ).collect(Collectors.toList());
			details.setContrastStallIds(stallIds);
			details.setType(reportForms.getType());
			stallLengthUp += stallIds.size();
//			stallLength +=stallIds.size();
			details.setContrastStallCount(stallIds.size());
			ResOwnerStallReportForms ownerReportForms = this.entRentedRecordClient.findOwnerStallReportForms(details);
			forms.setStartTime(details.getStartTime());
			forms.setEndTime(details.getEndTime());
			
			BigDecimal useTime = new BigDecimal(0);
			BigDecimal onceTime = new BigDecimal(0);
			BigDecimal useTimeUp = new BigDecimal(0);
			BigDecimal onceTimeUp = new BigDecimal(0);
			int stallUseCount = 0;
			
			if(ownerReportForms != null) {
				forms.setEntAuthStallRelative(ownerReportForms.getAuthStallNumberContrast());
				forms.setEntUseStallCount(ownerReportForms.getUseStall());
				forms.setEntUseStallRelative(ownerReportForms.getUseStallContrast());
				forms.setEntUseStallNumber(ownerReportForms.getUseStallCount());
				forms.setEntUseStallNumberRelative(ownerReportForms.getUseStallCountContrast());
				forms.setEntStallUseTime(ownerReportForms.getUseDuration());
				forms.setEntStallUseTimeRelative(ownerReportForms.getUseDurationContrast());
				/**
				 * getOneselfUseStallNumber 次数
				 * getOneselfUseStallNumberContrast 次数环比
				 * getOneselfUseStall  数
				 * authUseStallContrast 数
				 * authStallNumber 次数
				 * 
				 */
				// 使用数
				forms.setEntOneselfStallNumber(ownerReportForms.getOneselfUseStall());
				forms.setEntOneselfStallRelative(ownerReportForms.getOneselfUseStallContrast());
				forms.setEntAuthStallNumber(ownerReportForms.getAuthUseStall());
				forms.setEntAuthStallRelative(ownerReportForms.getAuthUseStallContrast());
//				forms.setValidTime(ownerReportForms.getStartTime());
				// 	使用次数
				forms.setEntOneselfUseStall(ownerReportForms.getOneselfUseStallNumber());
				forms.setEntOneselfUseStallRelative(ownerReportForms.getOneselfUseStallNumberContrast());
				forms.setEntAuthUseStall(ownerReportForms.getAuthStallNumber());
				forms.setEntAuthUseStallRelative(ownerReportForms.getAuthStallNumberContrast());
				forms.setEntOnceStallUseTime(ownerReportForms.getOnceStallUserTime());
				useTime = useTime.add(toBigDecimal(ownerReportForms.getUseDurationTimestamp()));
				onceTime = onceTime.add(toBigDecimal(ownerReportForms.getOnceStallUserTimestamp()));
				useTimeUp = useTimeUp.add(toBigDecimal(ownerReportForms.getUseDurationContrastTimestamp()));
				onceTimeUp = onceTimeUp.add(toBigDecimal(ownerReportForms.getOnceStallUserContrastTimestamp()));
				stallUseCount +=ownerReportForms.getUseStallCount();
				
			}
			stallIds = preStallList.stream().filter(s -> DateUtils.convert(s.getCreateTime(), null).getTime() <= details.getEndTime().getTime() &&s.getType() == (short)0).map(s -> s.getId() ).collect(Collectors.toList());
			details.setStallIds(stallIds);
			details.setStallCount(stallIds.size());
			stallLength +=stallIds.size();
			stallIds = preStallList.stream().filter(s -> DateUtils.convert(s.getCreateTime(), null).getTime() <= details.getContrastEndTime().getTime()  &&s.getType() == (short)0).map(s -> s.getId() ).collect(Collectors.toList());
			details.setContrastStallIds(stallIds);
			details.setContrastStallCount(stallIds.size());
			stallLengthUp += stallIds.size();
			ResTempStallReportForms tempReportForms = this.ordersService.findTempStallReportForms(details);
			if(tempReportForms != null) {
				forms.setTempOnceStallUseTime(tempReportForms.getOnceStallUserTime());
				forms.setTempIncome(tempReportForms.getOrderIncome());
				forms.setTempIncomeRelative(tempReportForms.getOrderIncomePercent());
				forms.setTempOrderNumber(tempReportForms.getOrderNumber());
				forms.setTempOrderRelative(tempReportForms.getOrderNumberPercent());
				forms.setTempAppointmentIncome(tempReportForms.getOrderAdvanceIncome());
				forms.setTempAppointmentRelative(tempReportForms.getOrderAdvanceIncomePercent());
				forms.setTempScanCodeIncome(tempReportForms.getOrderScanIncome());
				forms.setTempScanCodeRelative(tempReportForms.getOrderScanIncomePercent());
				forms.setTempShareIncome(tempReportForms.getOrderShareIncome());
				forms.setTempShareRelative(tempReportForms.getOrderShareIncomePercent());
				forms.setTempStallUseTime(tempReportForms.getOrderUseDuration());
				forms.setTempStallUseTimeRelative(tempReportForms.getOrderUseDurationPercent());
				forms.setTempAppointmentOrderRelative(tempReportForms.getOrderAdvancePercent());
				forms.setTempScanCodeOrderRelative(tempReportForms.getOrderScanPercent());
				forms.setTempShareRelative(tempReportForms.getOrderSharePercent());
				forms.setTempAppointmentOrder(tempReportForms.getOrderAdvanceNumber());
				forms.setTempScanCodeOrder(tempReportForms.getOrderScanNumber());
				forms.setTempShareOrder(tempReportForms.getOrderShareNumber());
				useTime = useTime.add(toBigDecimal(tempReportForms.getUseDurationTimestamp()));
				onceTime = onceTime.add(toBigDecimal(tempReportForms.getOnceStallUserTimestamp()));
				useTimeUp = useTimeUp.add(toBigDecimal(tempReportForms.getUseDurationContrastTimestamp()));
				onceTimeUp = onceTimeUp.add(toBigDecimal(tempReportForms.getOnceStallUserContrastTimestamp()));
				stallUseCount+=tempReportForms.getOrderNumber();
//				if(forms.getValidTime() == null || forms.getValidTime().getTime() >  tempReportForms.getStartTime().getTime()) {
//					forms.setValidTime(tempReportForms.getStartTime());
//				}
			}
//			stallIds = preStallList.stream().filter(s -> DateUtils.convert(s.getCreateTime(), null).getTime() <= details.getStartTime().getTime() &&(s.getType() == (short)0 ||s.getType() == (short)2 )).map(s -> s.getId() ).collect(Collectors.toList());
			if(useTime.doubleValue() != 0) {
				useTime = useTime.divide(new BigDecimal(stallLength),2,BigDecimal.ROUND_DOWN).divide(new BigDecimal(3600),2, BigDecimal.ROUND_DOWN)
						.divide(new BigDecimal(DateUtils.differentDaysByMillisecond(details.getStartTime(), details.getEndTime())),2, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN);
			}
			if(useTimeUp.doubleValue() != 0) {
				useTimeUp = useTimeUp.divide(new BigDecimal(stallLengthUp),2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(3600),2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(DateUtils.differentDaysByMillisecond(details.getStartTime(), details.getEndTime())),2, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN);
			}
			if(onceTime.doubleValue() != 0) {
				onceTime = onceTime.divide(new BigDecimal(3600),2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(stallUseCount),2, BigDecimal.ROUND_DOWN).setScale(2, BigDecimal.ROUND_DOWN);
			}
//			onceTimeUp = onceTimeUp.divide(new BigDecimal(stallUseCount== 0 ? 1 :stallUseCount)).setScale(2, BigDecimal.ROUND_DOWN);
			forms.setAllStallUseTime(useTime.doubleValue());
			forms.setAllOnceStallUseTime(onceTime.doubleValue());
			useTime = useTime.subtract(useTimeUp).multiply(new BigDecimal(100)).divide(useTimeUp.doubleValue() == 0 ? new BigDecimal(1):useTimeUp,2, BigDecimal.ROUND_DOWN).setScale(1, BigDecimal.ROUND_DOWN);
			forms.setAllStallUseTimeRelative(useTime.doubleValue()+"%");
		return forms;
	}

	
	
	private CacheUser getUser(HttpServletRequest request) {
		String os = this.userFactory.createTokenRedisKey(TokenUtil.getKey(request), request.getHeader("os"));
		CacheUser cu = (CacheUser) this.redisService.get(os);
		return cu;
	}
	
	private void checkPreAuth(HttpServletRequest request,Long preId) {
		CacheUser user = getUser(request);
		List<ResStaffPres> pres = staffAdminUserClient.findUserPres(user.getId());
		if(pres != null) {
			List<Long> collect = pres.stream().map(p -> p.getPreId()).collect(Collectors.toList());
			if(collect.contains(preId)){
				return;
			}
		}
		throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
	}
	
	public short setType(List<ResStallType> stallTypes ,Long preId) {
		for (ResStallType resStallType : stallTypes) {
			if(preId.longValue() == resStallType.getPreId().longValue()) {
				String types = resStallType.getTypes();
				if(types != null) {
					String[] strings = types.split(",");
					List<String> asList = Arrays.asList(strings);
					if(asList.contains("0") && asList.contains("2")) {
						return (short) 4;
					}else if(asList.contains("2")) {
						return (short) 2;
					}else if(asList.contains("0")) {
						return 0;
					}
				}
			}
		}
		return 5;
	}

	@Override
	@Transactional
	public void saveVirtualData(ReqDataCount dataCount) {
			//		首页
			ResPreList resPreList = new ResPreList();
			ResPreListType resPreListType = new ResPreListType();
			resPreList.setPreId(dataCount.getPreId());
			resPreList.setPreName(dataCount.getPreName());
			Map<String,Object> preList = new HashMap<>();
			preList.put("preId", dataCount.getPreId());
			resPreListType.setFixed(dataCount.getFixed());
			resPreListType.setFixedNumberUse(dataCount.getFixedNumberUse());
			resPreListType.setOrderConunt(dataCount.getOrderConunt());
			resPreListType.setOrderIncome(dataCount.getOrderIncome());
			resPreListType.setType(dataCount.getType());
			resPreList.setListType(resPreListType);
			// 详情 
			ResPreDetails resPreDetails = null;
			List<ResStaffDataCountVo<ResPreDetails>> resPreDetailsList = new ArrayList<>();
			String details = dataCount.getDetails();
			String[] floors = details.split(";");
//			"#{整层}#{总车位数},#{使用车位数},#{固定总数},#{固定使用车位数},#{临停总数},#{临停使用数},#{固定使用次数},#{固定自用},#{固定授权},#{临停订单收入},#{临停订单},#{临停已完成},#{临停进行中};"
			for (String floor : floors) {
				if(StringUtils.isBlank(floor)) {
					continue;
				}
				String[] data = floor.split(",");
				resPreDetails = new ResPreDetails();
				resPreDetails.setPreId(dataCount.getPreId());
				resPreDetails.setPreName(dataCount.getPreName());
				resPreDetails.setTotalStallNumber(toInt(data[1]));
				resPreDetails.setStallUseNumber(toInt(data[2]));
				resPreDetails.setEntTotalStallNumber(toInt(data[3]));
				resPreDetails.setEntStallUseNumber(toInt(data[4]));
				resPreDetails.setAppTotalStallNumber(toInt(data[5]));
				resPreDetails.setAppStallUseNumber(toInt(data[6]));
				resPreDetails.setEntStallUseCount(toInt(data[7]));
				resPreDetails.setEntHelpOneself(toInt(data[8]));
				resPreDetails.setEntAuth(toInt(data[9]));
				resPreDetails.setAppOrderIncome(toDouble(toDouble(data[10])));
				resPreDetails.setAppOrderCount(toInt(data[11]));
				resPreDetails.setAppOrderOver(toInt(data[12]));
				resPreDetails.setAppOrderUnfinished(toInt(data[13]));
				resPreDetails.setType(dataCount.getType());
				log.info(JsonUtil.toJson(resPreDetails));
				ResStaffDataCountVo<ResPreDetails> resPreListVo = new ResStaffDataCountVo<ResPreDetails>(floor, null, resPreDetails);
				resPreDetailsList.add(resPreListVo);
			}
			
				
				//报表
//				"#{固定车位数},#{固定车位数环比},#{固定使用次数},#{固定使用次数环比},#{固定自用},#{固定自用环比},#{固定授权},#{固定授权环比}";
//				",#{临停订单数},#{临停订单环比},#{临停收入},#{临停收入环比},#{临停预约收入},#{临停预约收入环比},#{临停扫码收入},#{临停扫码环比},#{临停分享收入},#{临停分享收入环比}";
//				#{整体使用时长},#{整体使用时长环比},#{固定使用时长},#{固定使用时长环比},#{临停使用时长},#{临停使用时长环比}"
				ResPreReportForms resPreReportForms = null;
				List<ResStaffDataCountVo<ResPreReportForms>> resPreReportFormsList = new ArrayList<>();
				String reportForms = dataCount.getReportForms();
				String[] floorsF = reportForms.split(";");
				for (String f : floorsF) {
					if(StringUtils.isBlank(f)) {
						continue;
					}
					resPreReportForms =  new ResPreReportForms();
					String[] data = f.split(",");
					resPreReportForms.setEntUseStallCount(toInt(data[2]));
					resPreReportForms.setEntUseStallRelative(toString(data[3]));
					resPreReportForms.setEntUseStallNumber(toInt(data[4]));
					resPreReportForms.setEntUseStallNumberRelative(toString(data[5]));
					resPreReportForms.setEntOneselfStallNumber(toInt(data[6]));
					resPreReportForms.setEntOneselfStallRelative(toString(data[7]));
					resPreReportForms.setEntAuthStallNumber(toInt(data[8]));
					resPreReportForms.setEntAuthStallRelative(toString(data[9]));
					resPreReportForms.setTempOrderNumber(toInt(data[10]));
					resPreReportForms.setTempOrderRelative(toString(data[11]));
					resPreReportForms.setTempIncome(toDouble(data[12]));
					resPreReportForms.setTempIncomeRelative(toString(data[13]));
					resPreReportForms.setTempAppointmentIncome(toDouble(data[14]));
					resPreReportForms.setTempAppointmentRelative(toString(data[15]));
					resPreReportForms.setTempScanCodeIncome(toDouble(data[16]));
					resPreReportForms.setTempScanCodeRelative(toString(data[17]));
					resPreReportForms.setTempShareIncome(toDouble(data[18]));
					resPreReportForms.setTempShareRelative(toString(data[19]));
					resPreReportForms.setAllStallUseTime(toDouble(data[20]));
					resPreReportForms.setAllStallUseTimeRelative(toString(data[21]));
					resPreReportForms.setEntStallUseTime(toDouble(data[22]));
					resPreReportForms.setEntStallUseTimeRelative(toString(data[23]));
					resPreReportForms.setTempStallUseTime(toDouble(data[24]));
					resPreReportForms.setTempStallUseTimeRelative(toString(data[25]));
					resPreReportForms.setEntOneselfUseStallRelative(toString(data[26]));
					resPreReportForms.setEntAuthUseStallRelative(toString(data[27]));
					resPreReportForms.setTempAppointmentOrderRelative(toString(data[28]));
					resPreReportForms.setTempScanCodeOrderRelative(toString(data[29]));
					resPreReportForms.setTempShareOrderRelative(toString(data[30]));
					
					resPreReportForms.setEntOneselfUseStall(toInt(data[31]));
					resPreReportForms.setEntAuthUseStall(toInt(data[32]));
					resPreReportForms.setTempAppointmentOrder(toInt(data[33]));
					resPreReportForms.setTempScanCodeOrder(toInt(data[34]));
					resPreReportForms.setTempShareOrder(toInt(data[35]));
					
					resPreReportForms.setAllOnceStallUseTime(toDouble(data[36]));
					resPreReportForms.setEntOnceStallUseTime(toDouble(data[37]));
					resPreReportForms.setTempOnceStallUseTime(toDouble(data[38]));
					
					resPreReportForms.setType(dataCount.getType());
					System.out.println(JsonUtil.toJson(resPreReportForms));
					ResStaffDataCountVo<ResPreReportForms> vo = new ResStaffDataCountVo<ResPreReportForms>(data[0].toString(), toInt(data[1]), resPreReportForms);
					resPreReportFormsList.add(vo);
				}
				Object ob = null;
				Set<Object> members = this.redisService.members(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key);
				for (Object object : members) {
					ResPreList o = (ResPreList)object;
					if(o != null && o.getPreId() != null && o.getPreId().longValue() == resPreList.getPreId().longValue()) {
						ob = object;
						break;
					}
					o = null;
				}
				if(ob != null) {
					members.remove(ob);
					members.add(resPreList);
					this.redisService.addAll(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key, members);
				}else {
					this.redisService.add(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key, resPreList);
				}
				Object hmGet = this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key,  dataCount.getPreId());
				if(hmGet != null) {
					this.redisService.remove(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key);
					hmGet = null;
				}
				this.redisService.hmSet(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key, dataCount.getPreId(), resPreDetailsList);
				
				hmGet = this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key,  dataCount.getPreId());
				if(hmGet != null) {
					this.redisService.remove(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key);
					hmGet = null;
				}
				this.redisService.hmSet(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key,dataCount.getPreId(),resPreReportFormsList);
		}
	
			private Integer toInt(Object obj) {
				if(obj == null) {
					return 0;
				}
				return Integer.parseInt(obj.toString());
			}
			private Double toDouble(Object obj) {
				if(obj == null) {
					return 0D;
				}
				return Double.parseDouble(obj.toString());
			}
			
			private String toString(Object obj) {
				if(obj == null) {
					return "0%";
				}
				return obj.toString()+"%";
			}
			private Long toLong(Object obj) {
				if(obj == null) {
					return 0L;
				}
				return Long.parseLong(obj.toString());
			}
	@Override
	public void stop() {
		this.redisService.set(RedisKey.STAFF_DATA_COUNT_RISE.key, false);
	}

	@Override
	public void delete(Long id) {
		Set<Object> members = this.redisService.members(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key);
		ResPreList preList = null;
		for (Object object : members) {
			preList = (ResPreList) object;
			if(preList.getPreId().longValue() == id.longValue()) {
				
				break;
			}
		}
		if(preList != null) {
			members.remove(preList);
			this.redisService.remove(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key);
			this.redisService.addAll(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key, members);
		}
		this.redisService.hDel(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key,id);
		this.redisService.hDel(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key,id);
	}

	@Override
	public void start() {
		this.redisService.set(RedisKey.STAFF_DATA_COUNT_RISE.key, true);
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		List<ReqDataCount>datacounts = new ArrayList<>();
		ReqDataCount datacount = null;
		Set<Object> members = this.redisService.members(RedisKey.STAFF_DATA_COUNT_PRE_LIST.key);
		ResPreList preList = null;
		List<ResStaffDataCountVo<ResPreDetails>> resPreDetailsList = null;
		List<ResStaffDataCountVo<ResPreReportForms>> forms = null;
		for (Object object : members) {
			if(object == null) {
				continue;
			}
			preList = (ResPreList) object;
			datacount = new ReqDataCount();
			datacount.setPreId(preList.getPreId());
			datacount.setPreName(preList.getPreName());
			datacount.setFixed(preList.getListType().getFixed());
			datacount.setFixedNumberUse(preList.getListType().getFixedNumberUse());
			datacount.setOrderConunt(preList.getListType().getOrderConunt());
			datacount.setOrderIncome(preList.getListType().getOrderIncome());
			datacount.setType(preList.getListType().getType());
			datacounts.add(datacount);
		}
//			resPreDetailsList = (List<ResStaffDataCountVo<ResPreDetails>>) this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_DETAILS.key, preList.getPreId());
//			forms = (List<ResStaffDataCountVo<ResPreReportForms>>) this.redisService.hmGet(RedisKey.STAFF_DATA_COUNT_PRE_FORMS.key, preList.getPreId());
		return new ViewPage(datacounts.size(), pageable.getPageSize(), datacounts);
	}
	
	
	private boolean getRise() {
		Object object = this.redisService.get(RedisKey.STAFF_DATA_COUNT_RISE.key);
		if(object != null && (boolean)object) {
			return true;
		}
		return false;
	}
	

	private BigDecimal toBigDecimal(Double obj) {
		if(obj == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(obj);
	}
}
