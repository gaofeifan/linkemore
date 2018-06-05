package cn.linkmore.order.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.order.client.PayClient;
import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
@Component
public class PayClientHystrix implements PayClient {
 
	public  ResOrderCheckout checkout(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		return null; 
	}
	 
	 
	public ResOrderConfirm confirm(@RequestBody ReqOrderConfirm roc) {
		return null;
	} 
	 
	public Boolean verify(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		return false;
	} 
	 
	public Boolean callback(@RequestParam("json") String json,@RequestParam("source") Integer source) {
		return false;
	}
}
