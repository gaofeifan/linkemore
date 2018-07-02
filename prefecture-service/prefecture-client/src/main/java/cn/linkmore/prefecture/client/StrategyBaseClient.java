package cn.linkmore.prefecture.client;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StrategyBaseClientHystrix;
import cn.linkmore.prefecture.request.ReqStrategy;
/**
 * 远程调用 - 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/feign/strategy", fallback=StrategyBaseClientHystrix.class,configuration = FeignConfiguration.class)
public interface StrategyBaseClient {
	
	/**
	 * 根据计费策略和进出时间获取计费信息
	 * 
	 * @param strategyId String
	 * @param beginTime Date
	 * @param endTime Date
	 */
	@RequestMapping(value = "/v2.0/fee", method=RequestMethod.POST)
	public Map<String, Object> fee(@RequestBody ReqStrategy reqStrategy);
	
}
