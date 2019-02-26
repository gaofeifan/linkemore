package cn.linkmore.order.core.pay;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.config.StartupRunner;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.order.controller.app.response.ResPayWeixinMini;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResWechatMiniOrder;

public class WechatMiniPay implements PaymentStrategy {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private WechatMiniClient wechatMiniClient = StartupRunner.get().getBean(WechatMiniClient.class);
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		ReqWechatMiniOrder wechat = new ReqWechatMiniOrder();
		wechat.setAddress(pay.getAddress());
		wechat.setAmount(pay.getAmount());
		wechat.setNumber(pay.getOrderCode());
		wechat.setOpenId(pay.getOpenId());
		ResWechatMiniOrder mini = this.wechatMiniClient.order(wechat);
		ResOrderConfirm confirm = new ResOrderConfirm();
		confirm.setAmount(new BigDecimal(pay.getAmount()));
		confirm.setNumber(pay.getOrderCode());
		confirm.setPayType((short) TradePayType.WECHAT_MINI.type);
		ResPayConfirm res = new ResPayConfirm();
		res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		res.setPayType(confirm.getPayType());
		res.setNumber(confirm.getNumber());
		ResPayWeixinMini wxMini = new ResPayWeixinMini();
		wxMini.setId(mini.getId());
		wxMini.setNonce(mini.getNonce());
		wxMini.setPack(mini.getPack());
		wxMini.setSign(mini.getSign());
		wxMini.setStamp(mini.getStamp());
		wxMini.setType(mini.getType());
		res.setWeixinMini(wxMini);
		return res;
	}

}
