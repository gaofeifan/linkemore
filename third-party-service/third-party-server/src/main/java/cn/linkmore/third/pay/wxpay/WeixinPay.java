package cn.linkmore.third.pay.wxpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;

import cn.linkmore.third.pay.PayConstants;

public class WeixinPay {
	 
	/**
	 * 微信支付
	 * @param remoteAddress
	 * @param openid
	 * @param orderCommonNo
	 * @param body格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	 * @param totalFee
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, Object> orderPay(String remoteAddress, String orderCommonNo, String body,
			double totalFee) throws JDOMException, IOException{ 
		BigDecimal bg = new BigDecimal(totalFee*100);
		int payprice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).intValue(); 
		SortedMap<String, Object> contentMap = new TreeMap<String, Object>();
		contentMap.put("appid", PayConstants.WX_APPID); // 公众账号 ID
		contentMap.put("mch_id", PayConstants.MCHID); // 商户号
		contentMap.put("nonce_str", PaySign.create_nonce_str()); // 随机字符串
		contentMap.put("body", body); // 商品描述
//		contentMap.put("body", URLEncoderUTF8.urlEnodeUTF8(body)); // 商品描述
		contentMap.put("out_trade_no", orderCommonNo); // 商户订单号
		contentMap.put("total_fee", payprice); // 订单总金额
		contentMap.put("spbill_create_ip", remoteAddress); // 订单生成的机器IP
		contentMap.put("notify_url", PayConstants.getOrderAsyncWechatUrl()); // 通知地址
		contentMap.put("trade_type", PayConstants.TRADE_TYPE_APP); // 交易类型
		String sign = PaySign.createSign(contentMap);// 签名
		contentMap.put("sign", sign);
		// 调用统一下单接口返回的值
		String result = HttpTool.sendPost(PayConstants.PREPAY_ID_URL, PaySign.sortmapTomap(contentMap), "UTF-8");
		// 调用统一接口返回的值XML转换为map格式
		Map<String, String> map = XMLUtil.doXMLParse(result); 
		
		Map<String,Object> m = new HashMap<>();
		if (map!=null&&map.get("return_code").equals("SUCCESS")&&map.get("result_code").equals("SUCCESS")) {
			SortedMap<String, Object> wxPayParamMap = new TreeMap<String, Object>();
			wxPayParamMap.put("appid", PayConstants.WX_APPID);
			wxPayParamMap.put("partnerid", PayConstants.MCHID);
			wxPayParamMap.put("prepayid", map.get("prepay_id"));
			wxPayParamMap.put("package", "Sign=WXPay");
			wxPayParamMap.put("noncestr", PaySign.create_nonce_str());
			wxPayParamMap.put("timestamp", PaySign.create_timestamp());
			String paySign = PaySign.createSign(wxPayParamMap);// 支付得到的签名
			wxPayParamMap.put("sign", paySign);
			m.put("order", wxPayParamMap);
		} else {
			m.put("order", null);
		}
		return m;
	}
	
	public static Map<String, Object> rechargePay(String remoteAddress, String orderCommonNo, String body,
			double totalFee) throws JDOMException, IOException{ 
		BigDecimal bg = new BigDecimal(totalFee*100);
		int payprice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).intValue(); 
		SortedMap<String, Object> contentMap = new TreeMap<String, Object>();
		contentMap.put("appid", PayConstants.WX_APPID); // 公众账号 ID
		contentMap.put("mch_id", PayConstants.MCHID); // 商户号
		contentMap.put("nonce_str", PaySign.create_nonce_str()); // 随机字符串
		contentMap.put("body", body); // 商品描述
//		contentMap.put("body", URLEncoderUTF8.urlEnodeUTF8(body)); // 商品描述
		contentMap.put("out_trade_no", orderCommonNo); // 商户订单号
		contentMap.put("total_fee", payprice); // 订单总金额
		contentMap.put("spbill_create_ip", remoteAddress); // 订单生成的机器IP
		contentMap.put("notify_url", PayConstants.getRechargeAsyncWechatUrl()); // 通知地址
		contentMap.put("trade_type", PayConstants.TRADE_TYPE_APP); // 交易类型
		String sign = PaySign.createSign(contentMap);// 签名
		contentMap.put("sign", sign);
		// 调用统一下单接口返回的值
		String result = HttpTool.sendPost(PayConstants.PREPAY_ID_URL, PaySign.sortmapTomap(contentMap), "UTF-8");
		// 调用统一接口返回的值XML转换为map格式
		Map<String, String> map = XMLUtil.doXMLParse(result); 
		
		Map<String,Object> m = new HashMap<>();
		if (map!=null&&map.get("return_code").equals("SUCCESS")&&map.get("result_code").equals("SUCCESS")) {
			SortedMap<String, Object> wxPayParamMap = new TreeMap<String, Object>();
			wxPayParamMap.put("appid", PayConstants.WX_APPID);
			wxPayParamMap.put("partnerid", PayConstants.MCHID);
			wxPayParamMap.put("prepayid", map.get("prepay_id"));
			wxPayParamMap.put("package", "Sign=WXPay");
			wxPayParamMap.put("noncestr", PaySign.create_nonce_str());
			wxPayParamMap.put("timestamp", PaySign.create_timestamp());
			String paySign = PaySign.createSign(wxPayParamMap);// 支付得到的签名
			wxPayParamMap.put("sign", paySign);
			m.put("order", wxPayParamMap);
		} else {
			m.put("order", null);
		}
		return m;
	}
	
	/**
	 * 微信支付 查询订单
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String wxPayOrderquery(String code) throws UnsupportedEncodingException{
		SortedMap<String, Object> contentMap = new TreeMap<String, Object>();
		contentMap.put("appid", PayConstants.WX_APPID); // 公众账号 ID
		contentMap.put("mch_id", PayConstants.MCHID); // 商户号
//		contentMap.put("transaction_id", transactionId); // 微信支付订单号 
		contentMap.put("out_trade_no", code); // 微信支付订单号 
		contentMap.put("nonce_str", PaySign.create_nonce_str()); // 随机字符串
		String sign = PaySign.createSign(contentMap);// 签名
		contentMap.put("sign", sign);
		// 调用统一下单接口返回的值
		String result = HttpTool.sendPost(PayConstants.ORDERQUERY_URL, PaySign.sortmapTomap(contentMap), "UTF-8");
		return result;
	}
	
	/**
	 * 微信支付 异步通知
	 * @param request
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,String> callBackUrl(HttpServletRequest request) throws JDOMException, IOException{
		//微信支付通知参数
		//接受微信支付通知post的参数
		Map<String, String> requestMap = XMLUtil.doXMLParse(request);
			//判断是否财付通签名
			if(PaySign.isValidSign(PaySign.strmapToTreeMap(requestMap))){
			}else{
				requestMap = null;
			}
		return requestMap;
	}
}

