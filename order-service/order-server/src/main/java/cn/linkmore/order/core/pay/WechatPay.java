package cn.linkmore.order.core.pay;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.config.StartupRunner;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.util.JsonUtil;

public class WechatPay implements PaymentStrategy {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private AppWechatClient appWechatClient = StartupRunner.get().getBean(AppWechatClient.class);
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		ReqAppWechatOrder reqawo = new ReqAppWechatOrder();
		reqawo.setAddress(pay.getAddress());
		reqawo.setAmount(pay.getAmount());
		reqawo.setNumber(pay.getOrderCode());
		ResAppWechatOrder rawo = this.appWechatClient.order(reqawo);
		log.info("get wechat order:{}", JsonUtil.toJson(rawo));
		ResPayConfirm res = new ResPayConfirm();
		res.setAmount(new BigDecimal(pay.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		res.setPayType((short) TradePayType.WECHAT.type);
		res.setNumber(pay.getOrderCode());
		cn.linkmore.order.controller.app.response.ResPayWeixin weixin = new cn.linkmore.order.controller.app.response.ResPayWeixin();
		weixin.setAppid(rawo.getAppid());
		weixin.setNoncestr(rawo.getNoncestr());
		weixin.setPartnerid(rawo.getPartnerid());
		weixin.setPrepayid(rawo.getPrepayid());
		weixin.setSign(rawo.getSign());
		weixin.setTimestamp(rawo.getTimestamp());
		res.setWeixin(weixin);
		return res;
	}
}
