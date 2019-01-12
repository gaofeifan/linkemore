package cn.linkmore.third.service;

import java.util.Map;

import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.request.ReqLoongPayVerifySign;
import cn.linkmore.third.response.ResLoongPay;

/**
 * 建行龙支付接口
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
public interface AppLoongPayService {

	/** 下单
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResLoongPay order(ReqLongPay longPay);

	/**
	 * @Description  通知消息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	boolean callbackMsg(Map<String, Object> map);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	boolean verifySigature(ReqLoongPayVerifySign verifySign);

}
