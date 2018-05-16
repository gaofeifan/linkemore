package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.common.client.hystrix.VehicleMarkClientHystrix;
import cn.linkmore.feign.FeignConfiguration;

@RestController
@FeignClient(value = "account-server", path = "/account/user", fallback=VehicleMarkClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserClient {
	
	@RequestMapping(value="/v2.0/logout/{userId}" ,method = RequestMethod.GET)
	public void logout(@PathVariable Long userId);
	
	@RequestMapping(value = "/v2.0/update_nickname/{userId}/{nickname}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(@PathVariable Long userId,@PathVariable String nickname);
	
	@RequestMapping(value = "/v2.0/update_sex/{sex}/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(@PathVariable Long userId,@PathVariable("sex")Integer sex);
	
	@RequestMapping(value = "/v2.0/update_vehicle", method = RequestMethod.PUT)
	public void updateVehicle(@RequestBody ReqVehicle req);
	
	@RequestMapping(value = "/v2.0/detail/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(@PathVariable Long userId) ;
	
	@RequestMapping(value = "/v2.0/send_code", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode( @RequestBody ReqBind bean) ;

	@RequestMapping(value = "/v2.0/update_mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile(@RequestBody ReqLogin bean);
	
	@RequestMapping(value = "/v2.0/update_wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateWechat(@RequestBody ReqWxLogin bean) ;
	
	@RequestMapping(value = "/v2.0/remove_wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId);
	
	@RequestMapping(value = "/v2.0/cache_key/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object getUserCacheKey(@PathVariable Long userId);
}
