package cn.linkmore.common.client.hystrix;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ReqNickname;
import cn.linkmore.account.response.ReqSex;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.common.client.UserClient;

@Component
public class UserClientHystrix implements UserClient{

	public void logout(@PathVariable Long userId) {
		
	}
	
	@Override
	public void updateNickname(@RequestBody ReqNickname nickname) {
	}

	@Override
	public void updateSex(@RequestBody ReqSex reqSex) {
	}
	
	public void updateVehicle(@RequestBody ReqVehicle req) {
		
	}
	
	public ResUserDetails detail(@PathVariable Long userId) {
		return new ResUserDetails();
	}
	
	public void sendCode( @RequestBody ReqBind bean) {
		
	}

	public void updateMobile(@RequestBody ReqLogin bean) {
		
	}
	
	public void updateWechat(@RequestBody ReqWxLogin bean) {
		
	}
	
	public void removeWechat(@PathVariable Long userId) {
		
	}

	@Override
	public ResUser getUserCacheKey(Long userId) {
		return null;
		
	}
	
	
}

