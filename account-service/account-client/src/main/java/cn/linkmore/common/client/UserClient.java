package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.common.client.hystrix.UserClientHystrix;
import cn.linkmore.feign.FeignConfiguration;

@RestController
@FeignClient(value = "account-server", path = "/account/user", fallback=UserClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserClient {
	
	
	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public void updateNickname(@RequestBody ReqUpdateNickname nickname);
	
	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public void updateSex(@RequestBody ReqUpdateSex reqSex);
	
	@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	public void updateVehicle(@RequestBody ReqUpdateVehicle req);
	
	@RequestMapping(value = "/v2.0/detail/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(@PathVariable Long userId) ;
	
	@RequestMapping(value = "/v2.0/send_code", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode( @RequestBody ReqBind bean) ;

	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile(@RequestBody ReqUpdateMobile bean);
	
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateAppfans(@RequestBody ReqUpdateMobile bean) ;
	
	@RequestMapping(value = "/v2.0/wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId);
	
	@RequestMapping(value = "/v2.0/cache/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResUser getUserCacheKey(@PathVariable Long userId);

	@RequestMapping(value = "/v2.0/resuser_mobile/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserLogin appLogin(@PathVariable String mobile);

	@RequestMapping(value="/v2.0/login",method = RequestMethod.POST)
	public ResUserLogin wxLogin(@RequestBody ReqUserAppfans appfans);
}
