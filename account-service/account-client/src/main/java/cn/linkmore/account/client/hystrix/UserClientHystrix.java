package cn.linkmore.account.client.hystrix;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	public void updateNickname(@RequestBody ReqUpdateNickname nickname) {
		
	};
	
	 
	public void updateSex(@RequestBody ReqUpdateSex reqSex) {
		
	}
	
	 
	public void updateVehicle(@RequestBody ReqUpdateVehicle req) {
		
	}
	
	 
	public ResUserDetails detail(@PathVariable("userId") Long userId) {
		return null;
	}
	
	 
	public void sendCode( @RequestBody ReqBind bean) {
		
	}

	 
	public void updateMobile(@RequestBody ReqUpdateMobile bean) {
		
	}
	
	 
	public void updateAppfans(@RequestBody ReqUserAppfans bean) {
		
	} 
	
	 
	public void removeWechat(@PathVariable("userId") Long userId) { 
	}
	
	 
	public ResUser getUserCacheKey(@PathVariable("userId") Long userId) {
		log.info("-------------------------account service user getUserCacheKey(userId) hystrix" + userId);
		return null;
	}

	 
	public ResUserLogin appLogin(@PathVariable("mobile") String mobile) {
		System.out.println("UserClientHystrix");
		return null;
	}
 
	public ResUserLogin wxLogin(@RequestBody ReqUserAppfans appfans) {
		return null;
	}
	
}

