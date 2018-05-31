package cn.linkmore.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/callback")
public class CallbackController {
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.POST)
	public void wechat(HttpServletResponse response, HttpServletRequest request) {

	}

	@RequestMapping(value = "/v2.0/alipay", consumes = { "application/x-www-form-urlencoded;charset=utf-8" })
	public void alipay(HttpServletResponse response, HttpServletRequest request) {
		 
	}
	
	@RequestMapping(value ="/v2.0/apple")
	public void apple(HttpServletResponse response,HttpServletRequest request){  
    	 
	}
}
