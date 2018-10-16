package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.service.SmsService;
/**
 * Controller - 短信服务
 * @author liwenlong
 * @version 2.0
 */
@RestController
@RequestMapping("/feign/sms")
public class SmsController {
	@Autowired
	private SmsService smsService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Boolean send(@RequestBody ReqSms req) {
		return this.smsService.send(req);
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Boolean sendtest() {
		ReqSms req = new ReqSms();
		req.setMobile("18310151716");
		req.setSt(Constants.SmsTemplate.ORDER_SUSPEND_NOTICE);
		return this.smsService.send(req);
	}
}
