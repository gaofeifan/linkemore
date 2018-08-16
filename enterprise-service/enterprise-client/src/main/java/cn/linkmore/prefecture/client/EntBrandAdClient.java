package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntBrandAdClientHystrix;
/**
 * 远程调用 - 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/ent-brand-ad", fallback=EntBrandAdClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntBrandAdClient {

	@RequestMapping(value = "/v2.0/find", method = RequestMethod.GET)
	@ResponseBody
	public ResBrandAd findByEntId(@RequestParam("entId") Long entId);
}
