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

import com.alipay.api.AlipayApiException;

import cn.linkmore.third.pay.alipay.Alipay;
import cn.linkmore.third.pay.alipay.OrderTrade;
import cn.linkmore.third.pay.wxpay.PaySign;
import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.service.AppAlipayService;
import cn.linkmore.util.JsonUtil;

/**
 * Controller - 支付宝支付
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/alipay")
public class AppAlipayController {
	
	@Autowired
	private AppAlipayService appAlipayService;
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqAppAlipay alipay) {
		return this.appAlipayService.order(alipay);
	}

	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json,@RequestParam("number")String number,@RequestParam("amount")String amount) {
		Boolean flag = false;
		OrderTrade or = new OrderTrade();
		or.setOutTradeNo(number);
		or.setTotalAmount(amount);
		Map<String,String> param = JsonUtil.toObject(json, HashMap.class);
		try {
			Map<String, String> orderMap = Alipay.verifyAsync(param, or);
			if(orderMap.get("status").equals("1")) {
				flag = true;
			}
		} catch (AlipayApiException e) { 
			e.printStackTrace();
		}
		return flag;
	}
}
