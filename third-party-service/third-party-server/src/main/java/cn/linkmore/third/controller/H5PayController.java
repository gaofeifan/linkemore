package cn.linkmore.third.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.service.H5PayService;

/**
 * Controller -支付接口
 * @author 常磊
 * @version 2.0
 */
@Controller
@CrossOrigin
@RequestMapping("/feign/h5-pay")
public class H5PayController {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	H5PayService h5TransactionService;

	/**
	 *  获取微信用户信息
	 */
	@RequestMapping(value="/wx",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree wxopenid(@RequestBody ReqH5Token reqH5Token) {
		log.info("wxopenid");
		return h5TransactionService.wxOpenid(reqH5Token);
	}
	
	/**
	 *  获取阿里用户信息
	 */
	@RequestMapping(value="/ali",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree aliopenid(@RequestBody ReqH5Token reqH5Token) {
		log.info("aliopenid");
		return h5TransactionService.aliOpenid(reqH5Token);
	}
	
	/**
	 *  微信支付
	 */
	@RequestMapping(value="/wxpay",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree wxpay(@RequestBody ReqH5Token reqH5Token) {
		log.info("wxpay");
		return h5TransactionService.aliOpenid(reqH5Token);
	}
	
	/**
	 *  支付宝支付
	 */
	@RequestMapping(value="/alipay",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree alipay(@RequestBody ReqH5Token reqH5Token) {
		log.info("aliopenid");
		return h5TransactionService.aliOpenid(reqH5Token);
	}
	

	
}
