package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.third.service.WechatService;
/**
 * Controller - 微信服务号
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/wechat")
public class WechatController {
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String getTicket(@RequestParam("actionName") String actionName,@RequestParam("sceneId")Long sceneId) {
		return wechatService.getTicket(actionName, sceneId);
	}
}
