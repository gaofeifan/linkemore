package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.ApplePayClient;
import cn.linkmore.third.request.ReqApplePay;
@Component
public class ApplePayClientHystrix implements ApplePayClient { 
	public String order(@RequestBody ReqApplePay order) {
		return null;
	}
	
	 
	public Boolean verify(@RequestParam("json")String json) {
		return false;
	}
}
