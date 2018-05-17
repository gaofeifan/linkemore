package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.response.ResFans;
import cn.linkmore.third.service.AppWechatService;
/**
 * Controller - 微信支付[APP]
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/third/app-wechat")
public class AppWechatController {
	@Autowired
	private AppWechatService appWechatService;
	
	/**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/fans/{code}", method = RequestMethod.GET) 
	@ResponseBody
	public ResFans getFans(@PathVariable("code") String code) {
		return this.appWechatService.getWechatFans(code);
	}
	
}
