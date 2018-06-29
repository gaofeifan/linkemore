package cn.linkmore.report.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.report.dao.cluster.ReportMonthClusterMapper;
import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResCost;
import cn.linkmore.report.response.ResIncome;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
import cn.linkmore.report.response.ResPullCost;
import cn.linkmore.report.response.ResRunTime;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResUserNum;
import cn.linkmore.report.service.ReportMonthService;

/**
 * 月报service
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class ReportMonthServiceImpl implements ReportMonthService {
	
	@Autowired
	private ReportMonthClusterMapper reportClusterMapper;
	
	@Override
	public List<ResCity> cityList() {
		return reportClusterMapper.cityList();
	}

	@Override
	public List<ResPre> preList(Map<String,Object> param) {
		return reportClusterMapper.preList(param);
	}

	@Override
	public Integer totalCount() {
		return reportClusterMapper.totalCount();
	}

	@Override
	public List<ResUserNum> userNumList(Map<String, Object> param) {
		return reportClusterMapper.userNumList(param);
	}

	@Override
	public List<ResNewUser> newUserList(Map<String, Object> param) {
		return reportClusterMapper.newUserList(param);
	}

	@Override
	public List<ResPull> pullList(Map<String, Object> param) {
		return reportClusterMapper.pullList(param);
	}

	@Override
	public List<ResStallAverage> stallAverageList(Map<String, Object> param) {
		return reportClusterMapper.stallAverageList(param);
	}

	@Override
	public List<ResOrder> orderList(Map<String, Object> param) {
		return reportClusterMapper.orderList(param);
	}

	@Override
	public List<ResOrder> ylOrderList(Map<String, Object> param) {
		return reportClusterMapper.ylOrderList(param);
	}

	@Override
	public List<ResOrder> newUserOrderList(Map<String, Object> param) {
		return reportClusterMapper.newUserOrderList(param);
	}

	@Override
	public List<ResOrder> oldUserOrderList(Map<String, Object> param) {
		return reportClusterMapper.oldUserOrderList(param);
	}

	@Override
	public List<ResRunTime> runtimeList(Map<String, Object> param) {
		return reportClusterMapper.runTimeList(param);
	}

	@Override
	public List<ResAveragePrice> averagePriceList(Map<String, Object> param) {
		return reportClusterMapper.averagePriceList(param);
	}

	@Override
	public List<ResIncome> incomeList(Map<String, Object> param) {
		return reportClusterMapper.incomeList(param);
	}

	@Override
	public List<ResCost> costList(Map<String, Object> param) {
		return reportClusterMapper.costList(param);
	}

	@Override
	public List<ResPullCost> pullCostList(Map<String, Object> param) {
		return reportClusterMapper.pullCostList(param);
	}
}
