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
import cn.linkmore.prefecture.client.hystrix.ChargeClientHystrix;
import cn.linkmore.prefecture.request.ReqPeriodCharge;

/**
 * @author zhengpengfei
 *
 */
@FeignClient(value = "abutting-period", path = "/charge/", fallback = ChargeClientHystrix.class, configuration = FeignConfiguration.class)
public interface ChargeClient {
	
	
	@RequestMapping(value = "/charge-list",method = RequestMethod.POST)
	@ResponseBody
	ViewPage list(@RequestBody ReqPeriodCharge reqPeriodCharge);
	
	@RequestMapping(value = "select-abutting-charge" , method = RequestMethod.POST)
	@ResponseBody
	Map<String,Object> selectAbuttingCharge();
	
	@RequestMapping(value = "save-abutting-charge" , method = RequestMethod.POST)
	@ResponseBody
	Map<String,Object> saveAbuttingCharge(@RequestBody ReqPeriodCharge reqPeriodCharge);

	@RequestMapping(value = "update-abutting-charge" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAbuttingCharge(@RequestBody ReqPeriodCharge reqPeriodCharge);
	
	@RequestMapping(value = "delete-abutting-charge" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAbuttingCharge(@RequestBody ReqPeriodCharge reqPeriodCharge);
	
	@RequestMapping(value = "select-charge-id" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectChargeById(@RequestBody ReqPeriodCharge reqPeriodCharge);
}
