package cn.linkmore.third.pay.wxpay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import cn.linkmore.third.pay.PayConstants;

public class PaySign {


	private static Logger log = LoggerFactory.getLogger(PaySign.class);
	/**
	 * 随机字符串(32位)
	 * 
	 * @return
	 */
	public static String create_nonce_str() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

	/**
	 * 微信支付签名算法sign
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(SortedMap<String, Object> parameters) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + PayConstants.KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}

	/**
	 * SortedMap转map
	 * 
	 * @param parameters
	 * @return
	 */
	public static Map<String, String> sortmapTomap(SortedMap<String, Object> parameters) {
		Map<String, String> map = new HashMap<String, String>();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry) it.next();
			String k = entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v)) {
				String value = v.toString();
				map.put(k, value);
			}
		}
		return map;
	}

	/**
	 * 时间戳
	 * 
	 * @return
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * map 转为 treemap
	 * 
	 * @param map
	 * @return
	 */
	public static TreeMap<String, String> strmapToTreeMap(
			Map<String, String> map) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		Set<Entry<String, String>> entry = map.entrySet();
		for (Entry<String, String> key : entry) {
			treeMap.put(key.getKey().toString(), key.getValue().toString());
		}
		return treeMap;
	}
	
	/**
	 * 微信 支付后 签名 验证
	 */
	public synchronized static boolean isValidSign(
			TreeMap<String, String> treeMap) {
		Set<Entry<String, String>> entry = treeMap.entrySet();
		StringBuffer sb = new StringBuffer();
		String signback = null;
		String input_charset = null;
		for (Entry<String, String> obj : entry) {
			String k = obj.getKey();
			String v = obj.getValue();
			if (v == null && v.equals(""))
				continue;
			if (k.equals("sign")) {
				signback = v;// 系统返回签名
				continue;
			}
			sb.append(k.toLowerCase() + "=" + v + "&");
		}
		String string1 = sb.toString();
		// 程序计算签名串
		String stringSignTemp = string1 + "key=" + PayConstants.KEY;
		// 程序计算财付通签名
		String sign = MD5Util.MD5Encode(stringSignTemp, input_charset)
				.toUpperCase();
		if (sign.equals(signback)) {
			log.info("DeBug财付通签名比对结果：TRUE");
			return true;
		} else {
			log.info("DeBug财付通签名比对结果：FALSE");
			return false;
		}
	}

}
