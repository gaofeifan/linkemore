package cn.linkmore.prefecture.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallBatteryLogClientHystrix;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
/**
 * 远程调用 - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/feign/stall-battery-log", fallback=StallBatteryLogClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallBatteryLogClient {
	
	@RequestMapping(value = "/v2.0/battery-log", method = RequestMethod.GET)
	public List<ResStallBatteryLog> findBatteryLogList(@RequestParam("stallId")Long stallId);
}
