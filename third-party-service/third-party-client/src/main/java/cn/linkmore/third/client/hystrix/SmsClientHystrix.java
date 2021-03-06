package cn.linkmore.third.client.hystrix;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.feign.ExceptionHandle;
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
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	ExceptionHandle exceptionHandle;
	public Boolean send(@RequestBody ReqSms req) {
		log.info("---------------------");
		log.info("sms service throw exception");
		log.info("---------------------");
		exceptionHandle.addFeignExceptionLog("sms service throw exception");
		return false;
	}
}
