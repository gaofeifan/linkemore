package cn.linkmore.account.client.hystrix;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;

@Component
public class UserClientHystrix implements UserClient{

	@Override
	public void updateNickname(@RequestBody ReqUpdateNickname nickname) {
	}

	@Override
	public void updateSex(@RequestBody ReqUpdateSex reqSex) {
	}
	
	public void updateVehicle(@RequestBody ReqUpdateVehicle req) {
		
	}
	
	public ResUserDetails detail(@PathVariable Long userId) {
		return new ResUserDetails();
	}
	
	public void sendCode( @RequestBody ReqBind bean) {
		
	}

	public void updateMobile(@RequestBody ReqUpdateMobile bean) {
		
	}
	
	@Override
	public void updateAppfans(ReqUserAppfans bean) {
	}

	public void removeWechat(@PathVariable Long userId) {
		
	}

	@Override
	public ResUser getUserCacheKey(Long userId) {
		return null;
		
	}

	@Override
	public ResUserLogin appLogin(@PathVariable String mobile){
		return null;
	}
	
	@Override
	public ResUserLogin wxLogin(@RequestBody ReqUserAppfans appfans) {
		return null;
	}
	
}

