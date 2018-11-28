package cn.linkmore.third.trade.wx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 支付参数封装类@常磊
 * 
 * @return
 */
public class UniPayReqData {
	/**
	 * @param deviceInfo门店号
	 * @param totalAmount支付金额
	 * @param body
	 * @param request
	 * @return
	 */
	public static Object getUniPayReq(String deviceInfo, String attach, String outTradeNo, BigDecimal totalAmount,
			String body, String timeStart, String timeExpire, String tradeType, String productId, String openid,
			String appID, String mchID, String key) {
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("appid", "");// 公众号ID
		parameterMap.put("mch_id", "");// 微信支付给的商户号
		parameterMap.put("key", "");// 微信支付给的key
		parameterMap.put("device_info", deviceInfo);// 门店号，指定的售卖机
		parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));// 随机字符串
		//StringUtils.abbreviate(body.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 600)
		parameterMap.put("body", "");// 商品描述
		// parameterMap.put("detail", detail);// 单品优惠字段(暂未上线)
		parameterMap.put("attach", attach);// 附加数据，在查询AP和支付通知时能够原样返回
		parameterMap.put("out_trade_no", outTradeNo);// 商户订单号
		parameterMap.put("fee_type", "CNY");// 标价币种
		BigDecimal total = totalAmount.multiply(new BigDecimal(100));
		java.text.DecimalFormat df = new java.text.DecimalFormat("0");
		parameterMap.put("total_fee", df.format(total));// 标价金额
		parameterMap.put("spbill_create_ip", ""); // 终端IP
		parameterMap.put("time_start", timeStart);
		parameterMap.put("time_expire", timeExpire);
		parameterMap.put("notify_url", ""); // 支付成功回调地址
		parameterMap.put("trade_type", tradeType == null ? "NATIVE" : tradeType);
		if (tradeType.equalsIgnoreCase("NATIVE"))
			parameterMap.put("product_id", productId);// 商品ID
		if (tradeType.equalsIgnoreCase("JSAPI"))
			parameterMap.put("openid", openid);// 商户ID
		String sign = PayCommonUtil.createSign(parameterMap);
		parameterMap.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameterMap);
		return requestXML;

	}

	public static <T> T getUniPayReq(String[] params, Class<T> cls) {
		T result = null;
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("appid", "");// 公众号ID
		parameterMap.put("mch_id", "");// 微信支付给的商户号
		try {
			if (cls.getSimpleName().equalsIgnoreCase("String"))
				result = cls.cast(PayCommonUtil.getRequestXml(parameterMap));
			else
				result = cls.cast(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
