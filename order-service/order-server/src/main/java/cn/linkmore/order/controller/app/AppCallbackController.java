package cn.linkmore.order.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.service.PayService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller - 第三方回调
 * 
 * @author liwenlong
 * @version 2。0
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/app/callback")
public class AppCallbackController {
	
	@Autowired
	private PayService payService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/v2.0/wechat/order", method = RequestMethod.POST)
	public void wechat(HttpServletResponse response, HttpServletRequest request) {
		log.info("wechat  async callback");
		this.payService.wechatOrderNotice(response,request);
	}
	@RequestMapping(value = "/v2.0/wechat-mini/order", method = RequestMethod.POST)
	public void wechatMini(HttpServletResponse response, HttpServletRequest request) {
		log.info("wechat  async callback");
		this.payService.wechatMiniOrderNotice(response,request);
	}

	@RequestMapping(value = "/v2.0/alipay/order", consumes = { "application/x-www-form-urlencoded;charset=utf-8" })
	public void alipay(HttpServletResponse response, HttpServletRequest request) {
		log.info("alipay async callback");
		this.payService.alipayOrderNotice(response,request);
	}
	
	@RequestMapping(value ="/v2.0/apple/order")
	public void apple(HttpServletResponse response,HttpServletRequest request){  
		log.info("apple async callback");
		this.payService.appleOrderNotice(response,request);
	}
}
