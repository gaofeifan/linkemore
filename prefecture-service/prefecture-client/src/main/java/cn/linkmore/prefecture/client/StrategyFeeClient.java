package cn.linkmore.prefecture.client;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StrategyFeeClientHystrix;
/**
 * 远程调用 - 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/feign/strategy/fee", fallback=StrategyFeeClientHystrix.class,configuration = FeignConfiguration.class)
public interface StrategyFeeClient {
	
	/**
	 * 计算总金额
	 * @param param
	 * startTime endTime stallId plateNo
	 * @return
	 */
	@RequestMapping(value = "/amount", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> amount(@RequestBody Map<String, Object> param);
	
}
