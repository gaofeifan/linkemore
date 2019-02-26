package cn.linkmore.order.core.pay;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.config.StartupRunner;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.third.client.AppLoongPayClient;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.response.ResLoongPay;

/**
 * 建行龙支付
 * @author   GFF
 * @Date     2019年1月25日
 * @Version  v2.0
 */
public class CCBPay implements PaymentStrategy {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private AppLoongPayClient appLoongPayClient = StartupRunner.get().getBean(AppLoongPayClient.class);
	
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		ReqLongPay longpay = new ReqLongPay();
		longpay.setAmount(new BigDecimal(pay.getAmount()));
		longpay.setOrderId(pay.getOrderCode());
		longpay.setUserId(pay.getUserId());
		ResLoongPay resLoongpay = appLoongPayClient.order(longpay);
		ResPayConfirm res = new ResPayConfirm();
		res.setPayType((short)TradePayType.LOONG.type);
		res.setNumber(pay.getOrderCode());
		res.setAmount(pay.getAmount());
		res.setResLoongPay(new cn.linkmore.order.controller.app.response.ResLoongPay(resLoongpay.getSign(), resLoongpay.getThirdAppInfo()));
		return res;
	}

}
