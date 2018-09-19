package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.order.controller.app.response.ResMonthCount;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResPreDataList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.order.service.StaffOrderService;
import cn.linkmore.util.DateUtils;

/**
 * @author GFF
 * @Date 2018年9月18日
 * @Version v2.0
 */
@Service
public class StaffOrderServiceImpl implements StaffOrderService {
	@Resource
	private OrdersClusterMapper ordersClusterMapper;
	@Resource
	private OrdersMasterMapper ordersMasterMapper;

	@Override
	public List<ResPreOrderCount> findPreListCount(Map<String, Object> param) {
		return this.ordersClusterMapper.findStaffPreListCount(param);
	}

	@Override
	public BigDecimal findDayIncome(Map<String, Object> map) {
		return this.ordersClusterMapper.findStaffDayIncome(map);
	}

	@Override
	public BigDecimal findAmountReportCount(Map<String, Object> map) {
		Date date = OrdersServiceImpl.getDateByType((short) Short.parseShort(map.get("type").toString()));
		map.put("startTime", date);
		return this.ordersClusterMapper.findStaffAmountReportCount(map);
	}

	@Override
	public Map<String, Object> findAmountReportList(Map<String, Object> map) {
		Date date = OrdersServiceImpl.getDateByType((short) Short.parseShort(map.get("type").toString()));
		short type = Short.parseShort(map.get("type").toString());
		map.put("startTime", date);
		List<ResPreDataList> list = this.ordersClusterMapper.findStaffPreDataList(map);
		BigDecimal decimal = this.ordersClusterMapper.findStaffAmountReportCount(map);
		int num = 0;
		switch (type) {
		case 0:
			num = 6;
			break;
		case 1:
			num = 14;
			break;
		case 2:
			num = 29;
			break;
		default:
			break;
		}
		List<ResPreDataList> lists = new ArrayList<>();
		ResPreDataList pre = null;
		for (; num >= 0; num--) {
			Date month = DateUtils.getDateByDay(-num);
			Date convert = DateUtils.convert(month, null);
			boolean falg = true;
			for (ResPreDataList resPreDataList : list) {
				if (resPreDataList.getDayTime().getTime() == convert.getTime()) {
					lists.add(resPreDataList);
					falg = false;
					break;
				}
			}
			if (falg) {
				pre = new ResPreDataList();
				pre.setDayNumber(0);
				pre.setDayAmount(new BigDecimal(0));
				pre.setDayTime(month);
				lists.add(pre);
			}
		}
		map = new HashMap<>();
		map.put("amount", decimal);
		map.put("list", lists);
		return map;
	}

	@Override
	public Integer findCarReportCount(Map<String, Object> map) {
		Date date = OrdersServiceImpl.getDateByType((short) Short.parseShort(map.get("type").toString()));
		map.put("startTime", date);
		return this.ordersClusterMapper.findStaffCarReportCount(map);
	}

	@Override
	public Map<String, Object> findCarReportList(Map<String, Object> map) {
		Date date = OrdersServiceImpl.getDateByType((short) Short.parseShort(map.get("type").toString()));
		short type = Short.parseShort(map.get("type").toString());
		map.put("startTime", date);
		Integer integer = this.ordersClusterMapper.findStaffCarReportCount(map);
		List<ResPreDataList> list = this.ordersClusterMapper.findStaffPreDataList(map);
		int num = 0;
		switch (type) {
		case 0:
			num = 6;
			break;
		case 1:
			num = 14;
			break;
		case 2:
			num = 29;
			break;
		default:
			break;
		}
		List<ResPreDataList> lists = new ArrayList<>();
		ResPreDataList pre = null;
		for (; num >= 0; num--) {
			Date month = DateUtils.getDateByDay(-num);
			Date convert = DateUtils.convert(month, null);
			boolean falg = true;
			for (ResPreDataList resPreDataList : list) {
				if (resPreDataList.getDayTime().getTime() == convert.getTime()) {
					lists.add(resPreDataList);
					falg = false;
					break;
				}
			}
			if (falg) {
				pre = new ResPreDataList();
				pre.setDayNumber(0);
				pre.setDayAmount(new BigDecimal(0));
				pre.setDayTime(month);
				lists.add(pre);
			}
		}
		map = new HashMap<>();
		map.put("list", lists);
		map.put("number", integer);
		return map;
	}

	@Override
	public List<ResIncome> findAmountMonthList(Map<String, Object> param) {
		Date date = OrdersServiceImpl.getDateByType(Short.parseShort(param.get("startTime").toString()));
		param.put("startTime", date);
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(Long.decode(param.get("now").toString()));
		Date now = instance.getTime();
		Map<String, Date> map = OrdersServiceImpl.getStartEndDate(now);
		param.put("monthStart", map.get("monthStart"));
		param.put("monthEnd", map.get("monthEnd"));
		ResMonthCount months = this.ordersClusterMapper.findStaffMonthCountByDate(param);
		List<Date> dates = DateUtils.getDates(now, Calendar.DAY_OF_MONTH, 10);
		param.put("startTime", dates.get(dates.size()-1));
		param.put("endTime", dates.get(0));
		List<ResIncomeList> list = this.ordersClusterMapper.findStaffAmountMonthList(param);
		List<ResIncomeList> newList = new ArrayList<>();
		ResIncomeList in = null;
		for (Date da : dates) {
			boolean falg = true;
			for (ResIncomeList resIncomeList : list) {
				if (da.getTime() == resIncomeList.getDate().getTime()) {
					newList.add(resIncomeList);
					falg = false;
					continue;
				}
			}
			if (falg) {
				in = new ResIncomeList();
				in.setDate(da);
				in.setDayAmount(new BigDecimal(0));
				in.setMonth(DateUtils.getFieldDataByDate(da, Calendar.MONTH));
				newList.add(in);
			}
		}

		ResIncome income = null;
		List<ResIncome> incomes = new ArrayList<>();
		Set<Integer> collect = newList.stream().map(i -> i.getMonth()).collect(Collectors.toSet());
		List<ResIncomeList> lists = new ArrayList<>();
		if (collect.size() > 1) {
			for (Integer integer : collect) {
				income = new ResIncome();
				if (integer.intValue() == months.getMonth()) {
					lists = new ArrayList<>();
					for (ResIncomeList resIncomeList : newList) {
						if (resIncomeList.getMonth().equals(integer)) {
							lists.add(resIncomeList);
						}
					}
					income.setList(lists);
					income.setMonthAmount(months.getMonthAmount());
					income.setDate(newList.get(0).getDate());
					if (incomes.size() == 0) {
						incomes.add(income);
					} else {
						incomes.add(incomes.set(0, income));
					}
				} else {
					lists = new ArrayList<>();
					map = OrdersServiceImpl.getStartEndDate(newList.get(newList.size() - 1).getMonth());
					param.put("monthStart", map.get("monthStart"));
					param.put("monthEnd", map.get("monthEnd"));
					ResMonthCount monthCount = this.ordersClusterMapper.findMonthCountByDate(param);
					for (ResIncomeList resIncomeList : newList) {
						if (integer.equals(resIncomeList.getMonth())) {
							lists.add(resIncomeList);
						}
					}
					income.setList(lists);
					income.setMonthAmount(monthCount.getMonthAmount());
					income.setDate(lists.get(0).getDate());
					incomes.add(income);
				}
			}
		} else {
			income = new ResIncome();
			income.setList(newList);
			income.setMonthAmount(months.getMonthAmount());
			income.setDate(map.get("monthStart"));
			incomes.add(income);
		}
		return incomes;
	}

	@Override
	public List<ResTrafficFlow> findCarMonthList(Map<String, Object> param) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(Long.decode(param.get("now").toString()));
		Date now = instance.getTime();
		Map<String, Date> map = OrdersServiceImpl.getStartEndDate(now);
		param.put("monthStart", map.get("monthStart"));
		param.put("monthEnd", map.get("monthEnd"));
		List<Date> dates = DateUtils.getDates(now, Calendar.DAY_OF_MONTH, 10);
		param.put("startTime",dates.get(dates.size()-1));
		param.put("endTime",dates.get(0));
		List<ResTrafficFlowList> list = this.ordersClusterMapper.findStaffCarMonthList(param);
		List<ResTrafficFlowList> newList = new ArrayList<ResTrafficFlowList>();
		ResTrafficFlowList in = null;
		for (Date da : dates) {
			boolean falg = true;
			for (ResTrafficFlowList resTrafficFlowList : list) {
				if (da.getTime() == resTrafficFlowList.getDate().getTime()) {
					newList.add(resTrafficFlowList);
					falg = false;
					continue;
				}
			}
			if (falg) {
				in = new ResTrafficFlowList();
				in.setDate(da);
				in.setCarDayTotal(0);
				in.setMonth(DateUtils.getFieldDataByDate(da, Calendar.MONTH));
				newList.add(in);
			}
		}
		ResMonthCount monthCount = this.ordersClusterMapper.findMonthCountByDate(param);
		List<ResTrafficFlow> flows = new ArrayList<>();
		ResTrafficFlow flow = null;
		Set<Integer> collect = newList.stream().map(traffic -> traffic.getMonth()).collect(Collectors.toSet());
		if (collect.size() > 1) {
			for (Integer integer : collect) {
				if (integer.shortValue() == monthCount.getMonth()) {
					flow = new ResTrafficFlow();
					List<ResTrafficFlowList> lists = new ArrayList<>();
					for (ResTrafficFlowList resTrafficFlowList : newList) {
						if (resTrafficFlowList.getMonth().equals(integer)) {
							lists.add(resTrafficFlowList);
						}
					}
					flow.setCarMonthTotal(monthCount.getMonthCarCount());
					flow.setTime(lists.get(0).getDate());
					flow.setTrafficFlows(lists);
					if (flows.size() == 0) {
						flows.add(flow);
					} else {
						flows.add(flows.set(0, flow));
					}
				} else {
					map = OrdersServiceImpl.getStartEndDate(integer);
					param.put("monthStart", map.get("monthStart"));
					param.put("monthEnd", map.get("monthEnd"));
					ResMonthCount resMonthCount = this.ordersClusterMapper.findMonthCountByDate(param);
					flow = new ResTrafficFlow();
					List<ResTrafficFlowList> lists = new ArrayList<>();
					for (ResTrafficFlowList resTrafficFlowList : newList) {
						if (resTrafficFlowList.getMonth().equals(integer)) {
							lists.add(resTrafficFlowList);
						}
					}
					flow.setCarMonthTotal(resMonthCount.getMonthCarCount());
					flow.setTime(lists.get(0).getDate());
					flow.setTrafficFlows(lists);
					flows.add(flow);
				}
			}
		} else {
			flow = new ResTrafficFlow();
			flow.setCarMonthTotal(monthCount.getMonthCarCount());
			flow.setTime(map.get("monthStart"));
			flow.setTrafficFlows(newList);
			flows.add(flow);
		}
		return flows;
	}
	
	
	
	

}
