package cn.linkmore.order.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;

@Component
public class OrderClientHystrix implements OrderClient {
	
	public void create(@RequestBody ReqOrderCreate roc){
		throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL);
	}
	
 
	public ResUserOrder userLatest(@PathVariable("userId") Long userId){
		return null;
	}
	 
	public ResUserOrder detail(@PathVariable("id") Long id){
		return null;
	}
	 
	public void down(@PathVariable("id") Long id) {
		throw new BusinessException(StatusEnum.ORDER_LOCKDOWN_FAIL);
	}
}
