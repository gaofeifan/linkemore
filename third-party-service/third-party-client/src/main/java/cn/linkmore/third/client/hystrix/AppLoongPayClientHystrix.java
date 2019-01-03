package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;

import cn.linkmore.third.client.AppLoongPayClient;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.response.ResLoongPay;

@Component
public class AppLoongPayClientHystrix implements AppLoongPayClient {

	@Override
	public ResLoongPay order(ReqLongPay alipay) {
		return null;
	}

	 
}
