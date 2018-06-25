package cn.linkmore.ops.account.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.ops.account.service.ReportDayService;
import cn.linkmore.report.client.ReportDayClient;
import cn.linkmore.report.request.ReqReportDay;
import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
import cn.linkmore.report.response.ResRunTime;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResUserNum;

/**
 * 日报service
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class ReportDayServiceImpl implements ReportDayService {
	
	@Autowired
	private ReportDayClient reportClusterClient;
	
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
	public List<ResUserNum> userNumList(ReqReportDay reportDay) {
		return reportClusterClient.userNumList(reportDay);
	}

	@Override
	public List<ResNewUser> newUserList(ReqReportDay reportDay) {
		return reportClusterClient.newUserList(reportDay);
	}

	@Override
	public List<ResPull> pullList(ReqReportDay reportDay) {
		return reportClusterClient.pullList(reportDay);
	}

	@Override
	public List<ResStallAverage> stallAverageList(ReqReportDay reportDay) {
		return reportClusterClient.stallAverageList(reportDay);
	}

	@Override
	public List<ResOrder> orderList(ReqReportDay reportDay) {
		return reportClusterClient.orderList(reportDay);
	}

	@Override
	public List<ResOrder> ylOrderList(ReqReportDay reportDay) {
		return reportClusterClient.ylOrderList(reportDay);
	}

	@Override
	public List<ResOrder> newUserOrderList(ReqReportDay reportDay) {
		return reportClusterClient.newUserOrderList(reportDay);
	}

	@Override
	public List<ResOrder> oldUserOrderList(ReqReportDay reportDay) {
		return reportClusterClient.oldUserOrderList(reportDay);
	}

	@Override
	public List<ResRunTime> runtimeList(ReqReportDay reportDay) {
		return reportClusterClient.runtimeList(reportDay);
	}

	@Override
	public List<ResAveragePrice> averagePriceList(ReqReportDay reportDay) {
		return reportClusterClient.averagePriceList(reportDay);
	}
}
