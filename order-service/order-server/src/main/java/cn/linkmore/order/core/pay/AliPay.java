package cn.linkmore.order.core.pay;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.config.StartupRunner;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.third.client.AppAlipayClient;
import cn.linkmore.third.request.ReqAppAlipay;

public class AliPay implements PaymentStrategy {
	private AppAlipayClient appAlipayClient = StartupRunner.get().getBean(AppAlipayClient.class);
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		ReqAppAlipay alipay = new ReqAppAlipay();
		alipay.setAmount(pay.getAmount());
		alipay.setNumber(pay.getOrderCode());
		String info = appAlipayClient.order(alipay);
		ResPayConfirm res = new ResPayConfirm();
		res.setAmount(new BigDecimal(pay.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		res.setPayType((short) TradePayType.ALIPAY.type);
		res.setAlipay(info);
		res.setNumber(pay.getOrderCode());
		return res;
	}
}
