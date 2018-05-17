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
import cn.linkmore.account.response.ReqNickname;
import cn.linkmore.account.response.ReqSex;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.common.client.hystrix.VehicleMarkClientHystrix;
import cn.linkmore.feign.FeignConfiguration;

@RestController
@FeignClient(value = "account-server", path = "/account/user", fallback=VehicleMarkClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserClient {
	
	@RequestMapping(value="/v2.0/logout/{userId}" ,method = RequestMethod.GET)
	public void logout(@PathVariable Long userId);
	
	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public void updateNickname(@RequestBody ReqNickname nickname);
	
	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public void updateSex(@RequestBody ReqSex reqSex);
	
	@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	public void updateVehicle(@RequestBody ReqVehicle req);
	
	@RequestMapping(value = "/v2.0/detail/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(@PathVariable Long userId) ;
	
	@RequestMapping(value = "/v2.0/send_code", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode( @RequestBody ReqBind bean) ;

	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile(@RequestBody ReqLogin bean);
	
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateWechat(@RequestBody ReqWxLogin bean) ;
	
	@RequestMapping(value = "/v2.0/wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId);
	
	@RequestMapping(value = "/v2.0/cache/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResUser getUserCacheKey(@PathVariable Long userId);
}
