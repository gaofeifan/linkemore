package cn.linkmore.third.pay.wxmini;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import cn.linkmore.third.response.ResWechatMiniOrder;
import cn.linkmore.util.JsonUtil;

public class WxMiniPay {
	private static Logger log = LoggerFactory.getLogger(WxMiniPay.class);

	/**
	 * 微信支付
	 * 
	 * @param remoteAddress
	 * @param openid
	 * @param orderCommonNo
	 * @param body格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	 * @param totalFee
	 * @param openid
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static ResWechatMiniOrder wxpay(String remoteAddress, String orderCommonNo,  String totalFee,
			String openid) throws IOException, DocumentException {
		// 微信 支付 单位：分
		int payprice = (int) (Double.valueOf(totalFee) * 100);
		SortedMap<String, Object> contentMap = new TreeMap<String, Object>();
		contentMap.put("appid", Constant.APPID); // 公众账号 ID
		contentMap.put("mch_id", Constant.MCHID); // 商户号
		contentMap.put("nonce_str", PaySign.create_nonce_str()); // 随机字符串
		contentMap.put("body", Constant.BODY_ORDER); // 商品描述
		contentMap.put("openid", openid); // openid 
		contentMap.put("out_trade_no", orderCommonNo); // 商户订单号
		contentMap.put("total_fee", payprice); // 订单总金额
		contentMap.put("spbill_create_ip", remoteAddress); // 订单生成的机器IP
		contentMap.put("notify_url", Constant.CALLBACK); // 异步通知地址
		contentMap.put("trade_type", Constant.TRADE_TYPE_JS); // 交易类型
		log.info(JsonUtil.toJson(contentMap));
		String sign = PaySign.createSign(contentMap);// 签名
		contentMap.put("sign", sign);
		// 调用统一下单接口返回的值
		String result = HttpTool.sendPost(Constant.PREPAY_ID_URL, PaySign.sortmapTomap(contentMap), "UTF-8");
		log.info(result);
		// 调用统一接口返回的值XML转换为map格式
		Map<String, String> map = doXMLParse(result); 
		SortedMap<String, Object> wxPayParamMap = new TreeMap<String, Object>();
		ResWechatMiniOrder bean = null;  
		if (map != null && map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
			bean = new ResWechatMiniOrder();
			bean.setId(Constant.APPID);
			bean.setPack("prepay_id=" + map.get("prepay_id").toString());
			bean.setNonce(PaySign.create_nonce_str());
			bean.setStamp(PaySign.create_timestamp());
			bean.setType("MD5"); 
			wxPayParamMap.put("appId", Constant.APPID);
			wxPayParamMap.put("package", "prepay_id=" + map.get("prepay_id").toString());
			wxPayParamMap.put("nonceStr",bean.getNonce());
			wxPayParamMap.put("timeStamp",bean.getStamp());
			wxPayParamMap.put("signType", "MD5");  
			bean.setSign(PaySign.createSign(wxPayParamMap)); 
		} 
		return bean;
	}

	/**
	 * 微信支付 异步通知
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> callBackUrl(HttpServletRequest request) throws Exception {
		// 微信支付通知参数
		// 接受微信支付通知post的参数
		Map<String, String> requestMap = parseXml(request);
		// 判断是否财付通签名
		if (PaySign.isValidSign(PaySign.strmapToTreeMap(requestMap))) {
		} else {
			requestMap = null;
		}
		return requestMap;

	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 微信支付 查询订单
	 * 
	 * @param code
	 *            账户充值编号
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String wxPayOrderquery(String code) throws UnsupportedEncodingException {
		SortedMap<String, Object> contentMap = new TreeMap<String, Object>();
		contentMap.put("appid", Constant.APPID); // 公众账号 ID
		contentMap.put("mch_id", Constant.MCHID); // 商户号
		contentMap.put("out_trade_no", code); // 微信支付订单号
		contentMap.put("nonce_str", PaySign.create_nonce_str()); // 随机字符串
		String sign = PaySign.createSign(contentMap);// 签名
		contentMap.put("sign", sign);
		// 调用统一下单接口返回的值
		String result = HttpTool.sendPost(Constant.ORDERQUERY_URL, PaySign.sortmapTomap(contentMap), "UTF-8");
		return result;
	} 
	
	/**
	 * 
	 * 解析String类型的xml流对象InputStream
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> doXMLParse(String strxml)throws IOException, DocumentException {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));;
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList){
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;

		return map;
	}
	 
}
