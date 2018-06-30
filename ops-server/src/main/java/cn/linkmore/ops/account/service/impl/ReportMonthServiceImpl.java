package cn.linkmore.ops.account.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.ops.account.service.ReportMonthService;
import cn.linkmore.report.client.ReportMonthClient;
import cn.linkmore.report.request.ReqReportMonth;
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

/**
 * 月报service
 * @author jiaohanbin
 * @Date 2018-06-30
 * @version 2.0
 *
 */
@Service
public class ReportMonthServiceImpl implements ReportMonthService {
	
	@Autowired
	private ReportMonthClient reportClusterClient;
	
	@Override
	public List<ResCity> cityList() {
		return reportClusterClient.cityList();
	}

	@Override
	public List<ResPre> preList(Map<String,Object> param) {
		return reportClusterClient.preList(param);
	}

	@Override
	public Integer totalCount() {
		return reportClusterClient.totalCount();
	}

	@Override
	public List<ResUserNum> userNumList(ReqReportMonth reportMonth) {
		return reportClusterClient.userNumList(reportMonth);
	}

	@Override
	public List<ResNewUser> newUserList(ReqReportMonth reportMonth) {
		return reportClusterClient.newUserList(reportMonth);
	}

	@Override
	public List<ResPull> pullList(ReqReportMonth reportMonth) {
		return reportClusterClient.pullList(reportMonth);
	}

	@Override
	public List<ResStallAverage> stallAverageList(ReqReportMonth reportMonth) {
		return reportClusterClient.stallAverageList(reportMonth);
	}

	@Override
	public List<ResOrder> orderList(ReqReportMonth reportMonth) {
		return reportClusterClient.orderList(reportMonth);
	}

	@Override
	public List<ResOrder> ylOrderList(ReqReportMonth reportMonth) {
		return reportClusterClient.ylOrderList(reportMonth);
	}

	@Override
	public List<ResOrder> newUserOrderList(ReqReportMonth reportMonth) {
		return reportClusterClient.newUserOrderList(reportMonth);
	}

	@Override
	public List<ResOrder> oldUserOrderList(ReqReportMonth reportMonth) {
		return reportClusterClient.oldUserOrderList(reportMonth);
	}

	@Override
	public List<ResRunTime> runtimeList(ReqReportMonth reportMonth) {
		return reportClusterClient.runtimeList(reportMonth);
	}

	@Override
	public List<ResAveragePrice> averagePriceList(ReqReportMonth reportMonth) {
		return reportClusterClient.averagePriceList(reportMonth);
	}

	@Override
	public List<ResCost> costList(ReqReportMonth reportMonth) {
		return reportClusterClient.costList(reportMonth);
	}

	@Override
	public List<ResIncome> incomeList(ReqReportMonth reportMonth) {
		return reportClusterClient.incomeList(reportMonth);
	}

	@Override
	public List<ResPullCost> pullCostList(ReqReportMonth reportMonth) {
		return reportClusterClient.pullCostList(reportMonth);
	}
}
