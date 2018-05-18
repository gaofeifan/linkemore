package cn.linkmore.prefecture.client;

import java.util.Date;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StrategyBaseClientHystrix;
/**
 * 远程调用 - 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/strategy", fallback=StrategyBaseClientHystrix.class,configuration = FeignConfiguration.class)
public interface StrategyBaseClient {
	
	/**
	 * 根据计费策略和进出时间获取计费信息
	 * 
	 * @param strategyId String
	 * @param beginTime Date
	 * @param endTime Date
	 */
	@RequestMapping(value = "/v2.0/fee", method=RequestMethod.GET)
	public Map<String, Object> fee(@RequestParam("strategyId") Long strategyId, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date beginTime,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime);
	
}
