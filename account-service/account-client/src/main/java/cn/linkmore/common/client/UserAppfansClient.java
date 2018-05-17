package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.common.client.hystrix.UserAppfansClientHystrix;
import cn.linkmore.feign.FeignConfiguration;

@RestController
@FeignClient(value = "account-server", path = "/account/user_appfans", fallback=UserAppfansClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserAppfansClient {
	
	@RequestMapping(value="/v2.0/{userId}",method = RequestMethod.GET)
	public ResUserAppfans selectByUserId(@PathVariable Long userId);
	
	@RequestMapping(value="/v2.0/login/{userId}",method = RequestMethod.GET)
	public ResUserLogin wxLogin(@RequestBody ReqUserAppfans appfans);
}
	
