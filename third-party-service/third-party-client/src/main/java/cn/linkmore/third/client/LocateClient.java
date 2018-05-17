package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.LocateClientHystrix;
import cn.linkmore.third.request.ReqLocate;
import cn.linkmore.third.response.ResLocate;

@FeignClient(value = "third-party-server", path = "/locate", fallback=LocateClientHystrix.class,configuration = FeignConfiguration.class)
public interface LocateClient {

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	ResLocate get(@RequestBody ReqLocate req) ;
}
