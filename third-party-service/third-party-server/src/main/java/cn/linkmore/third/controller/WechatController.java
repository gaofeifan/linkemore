package cn.linkmore.third.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqReAccessToken;
import cn.linkmore.third.request.ReqTemplateMsg;
import cn.linkmore.third.response.ResToken;
import cn.linkmore.third.response.ResWechatUserList;
import cn.linkmore.third.service.WechatService;
import cn.linkmore.util.JsonUtil;
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
	
	@RequestMapping(value="reset-token",method=RequestMethod.POST)
	@ResponseBody
	public ResToken resetToken(@RequestBody ReqReAccessToken accessToken) {
		return this.wechatService.resetToken(accessToken.getAppid(), accessToken.getAppsecret(), accessToken.getKey());
	}
	
	@RequestMapping(value="push-template",method=RequestMethod.POST)
	public void pushTemplateMsg(@RequestBody ReqTemplateMsg msg) {
		this.wechatService.pushTemplateMsg(msg);
	}

	@RequestMapping(value="user-list",method=RequestMethod.GET)
	@ResponseBody
	public ResWechatUserList getUserList(@RequestParam("token") String token) {
		ResWechatUserList userList = this.wechatService.getUserList(token);
		return userList;
	}
	
	
	
}
