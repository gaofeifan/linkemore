package cn.linkmore.monitor.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.monitor.client.hystrix.FeignExceptionLogClientHystrix;
import cn.linkmore.monitor.request.ReqExceptionLog;
@FeignClient(value = "monitor-server", path = "/feign/exception-log", fallback=FeignExceptionLogClientHystrix.class)
public interface FeignExceptionLogClient {

	@RequestMapping(value="/v2.0/info",method=RequestMethod.POST)
	public void info(@RequestBody ReqExceptionLog log);
	
	@RequestMapping(value="/v2.0/bind",method=RequestMethod.POST)
	public void bindException(@RequestBody ReqExceptionLog log);

	@RequestMapping(value="/v2.0/valid",method=RequestMethod.POST)
	public void validException(@RequestBody ReqExceptionLog log);

	@RequestMapping(value="/v2.0/feign",method=RequestMethod.POST)
	public void feignException(@RequestBody ReqExceptionLog log);
}
