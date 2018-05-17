package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.EnterpriseUserClientHystrix;
import cn.linkmore.account.response.ResEnterpriseUser;
import cn.linkmore.feign.FeignConfiguration;

@FeignClient(value = "account-server", path = "/account/enterprise_user", fallback=EnterpriseUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface EnterpriseUserClient {
	
	@RequestMapping(value="/v2.0/{userId}",method=RequestMethod.GET)
	public ResEnterpriseUser selectById(@PathVariable Long userId);
}
