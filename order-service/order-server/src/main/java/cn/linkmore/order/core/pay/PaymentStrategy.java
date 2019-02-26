package cn.linkmore.order.core.pay;

import cn.linkmore.order.controller.app.response.ResPayConfirm;

/**
 * 支付策略接口
 * @author   GFF
 * @Date     2019年1月29日
 * @Version  v2.0
 */
public interface PaymentStrategy {

	public ResPayConfirm pay(ReqPay pay);
}
