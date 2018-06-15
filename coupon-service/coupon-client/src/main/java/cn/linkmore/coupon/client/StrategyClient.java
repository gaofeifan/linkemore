package cn.linkmore.coupon.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.hystrix.StrategyClientHystrix;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqStrategy;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 策略
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/strategy", fallback=StrategyClientHystrix.class,configuration = FeignConfiguration.class)
public interface StrategyClient {
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategy record);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategy record);
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam("id") Long id);
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
}
