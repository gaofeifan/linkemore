package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntBrandUserClientHystrix;
/**
 * 远程调用 - 企业品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/ent-brand-user", fallback=EntBrandUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntBrandUserClient {
	
	@RequestMapping(value = "/v2.0/check-exist", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkExist(@RequestParam("userId") Long userId,@RequestParam("plateNo") String plateNo);
	
}
