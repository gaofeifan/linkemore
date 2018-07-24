package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.FeignEnterpriseClientHystrix;
/**
 * 远程调用 - 企业优惠劵
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/enterprise", fallback=FeignEnterpriseClientHystrix.class,configuration = FeignConfiguration.class)
public interface FeignEnterpriseClient {
	
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ResponseBody
	public ResEnterprise findById(@RequestParam("id") Long id);

}
