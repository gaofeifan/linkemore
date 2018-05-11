package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.request.ReqSms;
/**
 * hystrix - 短信
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class SmsClientHystrix implements SmsClient {
	public Boolean send(@RequestBody ReqSms req) {
		return false;
	}
}
