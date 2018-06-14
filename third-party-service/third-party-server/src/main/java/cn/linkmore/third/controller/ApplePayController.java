package cn.linkmore.third.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.pay.unionpay.AcpService;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.third.service.ApplePayService;
import cn.linkmore.util.JsonUtil;
/**
 * Controller - 银联苹果支付
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/apple-pay")
public class ApplePayController {
	
	@Autowired
	private ApplePayService applePayService;
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqApplePay order) {
		return this.applePayService.order(order);
	}
	
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json) {
		Map<String,String> param = JsonUtil.toObject(json,HashMap.class);
		Boolean flag = false;
		if(AcpService.validate(param,"UTF-8")&&"00".equals(param.get("respCode"))) {
			flag = true;
		}
		return flag;
	}
}
