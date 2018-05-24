package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.DockingClientHystrix;
import cn.linkmore.third.request.ReqOrder;

@FeignClient(value = "third-party-server", path = "/docking", fallback=DockingClientHystrix.class,configuration = FeignConfiguration.class)
public interface DockingClient {
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST)  
	public void order(@RequestBody ReqOrder ro);
}
