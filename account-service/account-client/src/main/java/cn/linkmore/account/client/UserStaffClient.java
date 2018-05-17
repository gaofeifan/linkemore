package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.UserStaffClientHystrix;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.feign.FeignConfiguration;

@Controller
@FeignClient(value = "account-server", path = "/account/user_staff", fallback=UserStaffClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserStaffClient {
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	public ResUserStaff selectById(@PathVariable ("id")Long id);
}
