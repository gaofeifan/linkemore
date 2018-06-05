package cn.linkmore.third.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.linkmore.third.pay.PayConstants;
import cn.linkmore.third.pay.alipay.Alipay;
import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.service.AppAlipayService;

@Service
public class AppAlipayServiceImpl implements AppAlipayService {

	@Override
	public String order(ReqAppAlipay alipay) {
		Map<String, String> m = Alipay.orderPay(alipay.getNumber(), alipay.getAmount(),
				PayConstants.BODY_ORDER); 
		return m.get("orderInfo"); 
	}
	
}
