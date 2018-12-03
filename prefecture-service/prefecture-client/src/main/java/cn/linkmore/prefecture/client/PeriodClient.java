/**
 * 
 */
package cn.linkmore.prefecture.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.PeriodClientHystrix;
import cn.linkmore.prefecture.request.ReqAbuttingHourPeriod;
import cn.linkmore.prefecture.request.ReqAbuttingPeriod;

/**
 * @author PPYX
 *
 */
@FeignClient(value = "abutting-period", path = "/period/", fallback = PeriodClientHystrix.class, configuration = FeignConfiguration.class)
public interface PeriodClient {
	
	@RequestMapping(value = "/period-list",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ReqAbuttingPeriod abuttingPeriod);
	
	@RequestMapping(value = "/select-period",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage selectPeriodById(@RequestBody ReqAbuttingPeriod abuttingPeriod);

	@RequestMapping(value = "save-abutting-period" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveAbuttingPeriod(@RequestBody ReqAbuttingPeriod abuttingPeriod);
	
	@RequestMapping(value = "update-abutting-period" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAbuttingPeriod(@RequestBody ReqAbuttingPeriod abuttingPeriod);
	
	@RequestMapping(value = "delete-abutting-period" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAbuttingPeriod(@RequestBody ReqAbuttingPeriod abuttingPeriod);
	
	@RequestMapping(value = "select-hour-period" , method = RequestMethod.POST)
	@ResponseBody
	public ViewPage selectHourPeriod(@RequestBody ReqAbuttingPeriod abuttingPeriod);
	
	@RequestMapping(value = "save-hour-period" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveHourPeriod(@RequestBody ReqAbuttingHourPeriod abuttingHourPeriod);
	
	@RequestMapping(value = "update-hour-period" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateHourPeriod(@RequestBody ReqAbuttingHourPeriod abuttingHourPeriod);
	
	@RequestMapping(value = "delete-hour-period" , method = RequestMethod.POST)
	@ResponseBody
	Map<String,Object> deleteHourPeriod(@RequestBody ReqAbuttingHourPeriod abuttingHourPeriod);

}
