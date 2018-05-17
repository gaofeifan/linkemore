package cn.linkmore.third.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.service.PushService;
/**
 * Controller -推送服务
 * @author liwenlong
 * @version 2.0
 */
@RestController
@RequestMapping("/push")
public class PushController {
	
	@Autowired
	private PushService pushService;
	
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST) 
	public void push(@RequestBody ReqPush rp) {
		this.pushService.push(rp);
	}

	@RequestMapping(value = "/v2.0", method = RequestMethod.PUT) 
	public void push(@RequestBody List<ReqPush> rps) {
		this.pushService.push(rps);
	}
}
