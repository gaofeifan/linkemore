package cn.linkmore.order.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.EntOrderClientHystrix;
import cn.linkmore.order.response.ResCharge;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResOrder;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResUserOrder;

@FeignClient(value = "order-server", path = "/ent/orders", fallback=EntOrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntOrderClient { 
	
	/**
	 * @Description  根据车区查询车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/plate-by-preid", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderPlate> findPlateByPreId(@RequestParam("preId")Long preId);

	@RequestMapping(value = "/day-income", method = RequestMethod.GET)
	@ResponseBody
	public BigDecimal findPreDayIncome( @RequestParam("preId") Long preId);

	@RequestMapping(value = "/traffic-flow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findTrafficFlow(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/proceeds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findProceeds(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/charge-detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResChargeDetail> findChargeDetail(@RequestBody Map<String, Object> param);
	
/*	@RequestMapping(value = "/charge-detail-new", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCharge> findChargeDetailNew(@RequestBody Map<String, Object> param);
*/

	@RequestMapping(value = "/traffic-flow-list", method = RequestMethod.POST)
	@ResponseBody
	public ResTrafficFlow findTrafficFlowList(@RequestBody Map<String, Object> param);

	/**
	 * @Description  查询收费列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/income-list", method = RequestMethod.POST)
	@ResponseBody
	public ResIncome findIncomeList(@RequestBody Map<String, Object> param);
	

	@RequestMapping(value = "/by-stall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreOrderCount> findPreCountByIds(@RequestBody List<Long> ids);

	@RequestMapping(value = "/proceeds-amount", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findProceedsAmount(@RequestBody Map<String, Object> param);

	@RequestMapping(value = "/traffic-flow-count", method = RequestMethod.POST)
	@ResponseBody
	public Integer findTrafficFlowCount(@RequestBody Map<String, Object> param);
	
	@RequestMapping(value = "/by-stall-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntOrder findOrderByStallId(@RequestParam("stallId") Long stallId);

	@RequestMapping(value = "/stall-latest", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder findStallLatest(@RequestParam("stallId") Long stallId);
}
