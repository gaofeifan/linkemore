package cn.linkmore.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.security.CacheUser;
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
import cn.linkmore.order.response.ResPreOrderDetails;
import cn.linkmore.order.response.ResTempStallReportForms;
import cn.linkmore.order.service.DataStatiscsService;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StaffAdminUserClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResAdminAuthPre;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResStaffPreDetails;
import cn.linkmore.prefecture.response.ResStaffPres;
import cn.linkmore.prefecture.response.ResStall;
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
		if(DATA_RISE) {
			log.info("展示自定义数据");
//			return doFindPreList()
			return null;
		}else {
			log.info("展示实际数据");
			List<ResStaffPres> pres = staffAdminUserClient.findUserPres(user.getId());
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
			if(resPres == null ) {
				return preListAndDetails;
			}
			preIds = resPres.stream().map(p -> p.getId()).collect(Collectors.toList());
			Date startTime = new Date();
			Calendar instance = Calendar.getInstance();
			Date date = DateUtils.getPast2String(-100,instance);
			ReqStaffPreOwnerStall details = new ReqStaffPreOwnerStall(preIds,date,startTime);
			details.setType((short)1);
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
						preList.getListType().setFixed(staffPre.getPreType().getOwnerStallNumber());
						for (ResStaffOwnerUseStall resStaffOwnerUseStall : preNumbers) {
							if(resStaffPres.getId().longValue() == resStaffOwnerUseStall.getPreId().longValue()) {
								preList.getListType().setFixedNumberUse(resStaffOwnerUseStall.getUseNumber());
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
		Map<String, Object> param = new HashMap<>();
		param .put("preId",details.getPreId());
		param.put("floor", details.getFloor());
		List<ResStall> preStallList = this.stallClient.findPreStallList(param );
		if(preStallList == null ) {
			return preDetails;
		}
		preDetails.setTotalStallNumber(de.getTotalStallNumber());
		preDetails.setEntTotalStallNumber(de.getOwnerStallNumber());
		preDetails.setEntStallUseNumber(de.getOwnerUseStallNumber());
		preDetails.setAppTotalStallNumber(de.getTempStallNumber());
		preDetails.setAppStallUseNumber(de.getTempUseStallNumber());
		preDetails.setStallUseNumber(de.getUseStalNumber());
		
		List<Long> stallIds = preStallList.stream().map(s->s.getId()).collect(Collectors.toList());
		ResOwnerStallDetails ownerStall = this.entRentedRecordClient.findPreDetails(new cn.linkmore.enterprise.request.ReqPreDetails(null,stallIds,details.getPreId()));
		if(ownerStall != null) {
			preDetails.setEntAuth(ownerStall.getAuth());
			preDetails.setEntHelpOneself(ownerStall.getHelpOneself());
			preDetails.setEntStallUseNumber(ownerStall.getUseCount());
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
		Map<String, Object> param = new HashMap<>();
		param.put("preId", reportForms.getPreId());
		param.put("floor", reportForms.getFloor());
		List<ResStall> preStallList = this.stallClient.findPreStallList(param );
		if(preStallList == null || preStallList.size() == 0) {
			return forms;
		}
		List<Long> stallIds = preStallList.stream().map(s -> s.getId()).collect(Collectors.toList());
		details.setStallIds(stallIds);
		if(reportForms.getType() == 0) {
			Date date = DateUtils.getPast2String(-1, Calendar.getInstance());
			details.setStartTime(date);
			details.setEndTime(date);
			date = DateUtils.getPast2String(-2, Calendar.getInstance());
			details.setContrastStartTime(date);
			details.setContrastStartTime(date);
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
			}
			details.setType(reportForms.getType());
			details.setStallCount(preStallList.size());
			ResOwnerStallReportForms ownerReportForms = this.entRentedRecordClient.findOwnerStallReportForms(details);
			forms.setStartTime(details.getStartTime());
			forms.setEndTime(details.getEndTime());
			if(ownerReportForms != null) {
				forms.setEntAuthStallNumber(ownerReportForms.getAuthStallNumber());
				forms.setEntAuthStallRelative(ownerReportForms.getAuthStallNumberContrast());
				forms.setEntOneselfStallNumber(ownerReportForms.getOneselfUseStallNumber());
				forms.setEntOneselfStallRelative(ownerReportForms.getOneselfUseStallNumberContrast());
				forms.setEntUseStallCount(ownerReportForms.getUseStall());
				forms.setEntUseStallRelative(ownerReportForms.getUseStallContrast());
				forms.setEntUseStallNumber(ownerReportForms.getUseStallCount());
				forms.setEntUseStallNumberRelative(ownerReportForms.getUseStallCountContrast());
				forms.setEntStallUseTime(ownerReportForms.getUseDuration());
				forms.setEntStallUseTimeRelative(ownerReportForms.getUseDurationContrast());
			}
			ResTempStallReportForms tempReportForms = this.ordersService.findTempStallReportForms(details);
			if(tempReportForms != null) {
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
			}
		}
		return forms;
	}

	private CacheUser getUser(HttpServletRequest request) {
		String os = this.userFactory.createTokenRedisKey(TokenUtil.getKey(request), request.getHeader("os"));
		CacheUser cu = (CacheUser) this.redisService.get(os);
		return cu;
	}
	
}
