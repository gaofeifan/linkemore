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

import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;
import cn.linkmore.third.service.H5PayService;

/**
 * @Description    支付业务受理
 * @Author           changlei
 * @Date               2018.11.28
 */ 
@Controller
@CrossOrigin
@RequestMapping("/feign/h5-pay")
public class H5PayController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	H5PayService h5TransactionService;

	/**
	 * 获取微信用户信息@changlei
	 */
	@RequestMapping(value = "/wx", method = RequestMethod.POST)
	@ResponseBody
	public ResH5Degree wxopenid(@RequestBody ReqH5Token reqH5Token) {
		log.info("wxopenid");
		return h5TransactionService.wxOpenid(reqH5Token);
	}

	/**
	 * 获取阿里用户信息@changlei
	 */
	@RequestMapping(value = "/ali", method = RequestMethod.POST)
	@ResponseBody
	public ResH5Degree aliopenid(@RequestBody ReqH5Token reqH5Token) {
		log.info("aliopenid");
		return h5TransactionService.aliOpenid(reqH5Token);
	}

	/**
	 * 微信支付@changlei
	 */
	@RequestMapping(value = "/wxpay", method = RequestMethod.POST)
	@ResponseBody
	public ResH5Term wxpay(@RequestBody ReqH5Term reqH5Term) {
		log.info("wxpay");
		return h5TransactionService.wxpay(reqH5Term);
	}

	/**
	 * 支付宝支付@changlei
	 */
	@RequestMapping(value = "/alipay", method = RequestMethod.POST)
	@ResponseBody
	public String alipay(@RequestBody ReqH5Term reqH5Term) {
		log.info("alipay");
		return h5TransactionService.alipay(reqH5Term);
	}

}
