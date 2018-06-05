package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.AppAlipayClient;
import cn.linkmore.third.request.ReqAppAlipay;

@Component
public class AppAlipayClientHystrix implements AppAlipayClient {

	@Override
	public String order(@RequestBody ReqAppAlipay alipay) {
		return null;
	}
	 
	public Boolean verify(@RequestParam("json")String json,@RequestParam("number")String number,@RequestParam("amount")String amount) {
		return false;
	}
}
