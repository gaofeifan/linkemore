package cn.linkmore.order.core.pay;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.config.StartupRunner;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.response.ResOrderWeixin;
import cn.linkmore.third.client.ApplePayClient;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.util.JsonUtil;

public class UnionPay implements PaymentStrategy {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private ApplePayClient applePayClient = StartupRunner.get().getBean(ApplePayClient.class);
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		ReqApplePay rap = new ReqApplePay();
		rap.setTimestramp(new Date().getTime());
		rap.setAmount(pay.getAmount());
		rap.setNumber(pay.getOrderCode());
		String tn = this.applePayClient.order(rap);
		ResPayConfirm res = new ResPayConfirm();
		res.setAmount(new BigDecimal(pay.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		res.setPayType((short) TradePayType.UNION.type);
		res.setUnion(tn);
		res.setNumber(pay.getOrderCode());
		return res;
	}

}
