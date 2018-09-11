package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntBrandAdClientHystrix;
import cn.linkmore.prefecture.client.hystrix.EntRentedRecordClientHystrix;
@FeignClient(value = "enterprise-server", path = "/feign/ent-rented", fallback=EntRentedRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntRentedRecordClient {

	@RequestMapping(value = "/down-time", method = RequestMethod.GET)
	@ResponseBody
	public void updateDownTime(@RequestParam("stallId") Long stallId);
}
