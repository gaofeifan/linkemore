package cn.linkmore.order.client.hystrix;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.order.client.StaffOrderClient;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
@Component
public class StaffOrderClientHystrix implements StaffOrderClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public List<ResPreOrderCount> findPreListCount(Map<String, Object> param) {
		log.info("====Hystrix=====List<ResPreOrderCount> findPreListCount(Map<String, Object> param)");
		return null;
	}
	@Override
	public BigDecimal findDayIncome(Map<String, Object> map) {
		log.info("====Hystrix===== BigDecimal findDayIncome(Map<String, Object> map)");
		return null;
	}
	@Override
	public BigDecimal findAmountReportCount(Map<String, Object> map) {
		log.info("====Hystrix===== BigDecimal findAmountReportCount(Map<String, Object> map)");
		return null;
	}
	@Override
	public Map<String,Object> findAmountReportList(Map<String, Object> map) {
		log.info("====Hystrix===== Map<String,Object> findAmountReportCount(Map<String, Object> map)");
		return null;
	}
	@Override
	public Integer findCarReportCount(Map<String, Object> map) {
		log.info("====Hystrix===== BigDecimal findCarReportCount(Map<String, Object> map)");
		return null;
	}
	@Override
	public Map<String,Object> findCarReportList(Map<String, Object> map) {
		log.info("====Hystrix===== Map<String,Object> findCarReportCount(Map<String, Object> map)");
		return null;
	}
	@Override
	public List<ResTrafficFlow> findCarMonthList(Map<String, Object> map) {
		log.info("====Hystrix===== List<ResTrafficFlow> findCarMonthList(Map<String, Object> map)");
		return null;
	}
	@Override
	public List<ResIncome> findAmountMonthList(Map<String, Object> map) {
		log.info("====Hystrix===== List<ResIncome> findAmountMonthList(Map<String, Object> map)");
		return null;
	}
	@Override
	public List<ResChargeDetail> findAmountDetail(Map<String, Object> param) {
		log.info("====Hystrix===== List<ResChargeDetail> findAmountDetail(Map<String, Object> param");
		return null;
	}
	
	
	

}
