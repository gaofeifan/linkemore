package cn.linkmore.prefecture.client;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResFixedPlate;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.FixedPlateClientHystrix;

@FeignClient(value = "enterprise-server", path = "/feign/fixed-plate", fallback=FixedPlateClientHystrix.class,configuration = FeignConfiguration.class)
public interface FixedPlateClient {

	@RequestMapping(value = "/find-plate-nos", method = RequestMethod.GET)
	@ResponseBody
	public ResFixedPlate findPlateNosByStallId(@RequestParam("stallId") Long stallId);
	
}
