package cn.linkmore.third.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.third.config.UnionPayConfig;
import cn.linkmore.third.pay.unionpay.Unionpay;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.third.service.ApplePayService;

@Service
public class ApplePayServiceImpl implements ApplePayService {
	@Autowired
	private UnionPayConfig unionPayConfig;
	
	@Override
	public String order(ReqApplePay order) {
		return Unionpay.create(order, unionPayConfig);
	}
}
