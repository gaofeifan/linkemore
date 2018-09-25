package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.service.PushService;

/**
 * Controller -推送服务
 * @author cl
 * @version 2.0
 */
@RestController
@RequestMapping("/feign/send")
public class SendController {

	@Autowired
	private PushService pushService;
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST) 
	public void send(@RequestBody ReqPush rp) {
		this.pushService.send(rp);
	}
	
	@RequestMapping(value = "/v3.0", method = RequestMethod.POST) 
	public void give(@RequestBody ReqPush rp) {
		this.pushService.give(rp);
	}
	
}
