package cn.linkmore.third.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.service.AppLongPayService;

/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/long-pay")
public class AppLongPayController {

	@Resource
	private AppLongPayService appLongPayService;
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqLongPay longPay) {
		return this.appLongPayService.order(longPay);
	}
	
	@RequestMapping(value="/v3.0/callback-msg",method=RequestMethod.POST)
	@ResponseBody
	public boolean callbackMsg(@RequestBody Map<String, Object> map) {
		return this.appLongPayService.callbackMsg(map);
	}
}
