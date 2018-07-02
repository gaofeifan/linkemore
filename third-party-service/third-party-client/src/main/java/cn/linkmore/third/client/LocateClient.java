package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.LocateClientHystrix;
import cn.linkmore.third.response.ResLocate;

@FeignClient(value = "third-party-server", path = "/feign/locate", fallback=LocateClientHystrix.class,configuration = FeignConfiguration.class)
public interface LocateClient {

	@RequestMapping(value = "/v2.0", method=RequestMethod.GET)
	@ResponseBody
	ResLocate get(@RequestParam(value="longitude",required=true)String longitude,@RequestParam(value="latitude",required=true)String latitude) ;
}
