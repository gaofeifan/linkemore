package cn.linkmore.third.service;

import java.util.Map;

import cn.linkmore.third.request.ReqLongPay;

/**
 * 建行龙支付接口
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
public interface AppLongPayService {

	/** 下单
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	String order(ReqLongPay longPay);

	/**
	 * @Description  通知消息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	boolean callbackMsg(Map<String, Object> map);

}
