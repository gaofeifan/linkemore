package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.TargetDayClientHystrix;
/**
 * 远程调用 - 车区每日目标
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/target_day", fallback=TargetDayClientHystrix.class,configuration = FeignConfiguration.class)
public interface TargetDayClient {
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
}
