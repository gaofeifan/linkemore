package cn.linkmore.order.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.StaffOrderClientHystrix;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;

@FeignClient(value = "order-server", path = "/staff/orders", fallback=StaffOrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface StaffOrderClient { 
	

	@RequestMapping(value = "/pre-count", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreOrderCount> findPreListCount(@RequestBody Map<String, Object> param);

	@RequestMapping(value = "/day-income", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findDayIncome(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/amount-report-count", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findAmountReportCount(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/amount-report-list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findAmountReportList(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/car-report-count", method = RequestMethod.POST)
	@ResponseBody
	public Integer findCarReportCount(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/car-report-list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findCarReportList(@RequestBody Map<String, Object> map);
	
	@RequestMapping(value = "/car-month-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTrafficFlow> findCarMonthList(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/amount-month-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResIncome> findAmountMonthList(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/amount-detail-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResChargeDetail> findAmountDetail(@RequestBody Map<String, Object> param);
}
