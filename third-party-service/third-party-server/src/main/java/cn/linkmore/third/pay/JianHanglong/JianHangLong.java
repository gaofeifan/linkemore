package cn.linkmore.third.pay.JianHanglong;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CCBSign.RSASig;
import cn.linkmore.third.config.AppLongPayConfig;
import cn.linkmore.third.pay.wxmini.MD5Util;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.util.JsonUtil;
import netpay.merchant.crypto.MD5;
import netpay.merchant.crypto.MD5withRSA;
import netpay.merchant.crypto.RSAPubKey;
/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
public class JianHangLong {
	
	private static Logger log = LoggerFactory.getLogger(JianHangLong.class);
	private static final List<String > macParam = Arrays.asList(
			"SMERID","SMERNAME","SMERTYPEID","SMERTYPE","TRADECODE","TRADENAME",
			"SMEPROTYPE","PRONAME","THIRDAPPINFO","TIMEOUT","NoCredit","NoDebit");
	public static String create(ReqLongPay pay,AppLongPayConfig config) {
		log.info("ReqLongPay====="+JsonUtil.toJson(pay));
		log.info("AppLongPayConfig====="+JsonUtil.toJson(config));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("MERCHANTID", config.getMerchantId());
		map.put("POSID", config.getPosId());
		map.put("BRANCHID", config.getBranchId());
		map.put("ORDERID", pay.getOrderId());
		map.put("PAYMENT", pay.getAmount());
		map.put("CURCODE", 01);
		map.put("REMARK1", "");
		map.put("REMARK2", "");
		map.put("TXCODE", "520100");
//		map.put("MAC", getMD5MAC());//md5加密mac地址
		map.put("TYPE", 1);
		map.put("PUB", "");//公钥后30位
		map.put("GATEWAY", 0);
		map.put("CLIENTIP", pay.getUserId());
		map.put("REGINFO", "");//客户注册信息
		map.put("PROINFO", "");//商品信息
		map.put("REFERER", "");//商户URL
		map.put("THIRDAPPINFO", "");//客户端标识
		map.put("TIMEOUT", "");//订单超时时间
		log.info("data====="+JsonUtil.toJson(map));
		send(config,map, config.getUrl());
		return "";
	}

	/*
	private static void send(AppLongPayConfig config,Map<String,Object> map,String url) {
		StringBuilder form = new StringBuilder();
		form.append("<form action='"+url+"?' method='post'>\n" );
		StringBuilder mac = new StringBuilder();
		StringBuilder propers = new StringBuilder();
		Set<Entry<String,Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			if(entry.getValue() != null && !entry.getValue().toString().equals("")) {
				if(macParam.contains(entry.getValue())) {
					if(mac.length() != 0) {
						mac.append("&");
					}
					mac.append(entry.getKey()+"=").append(entry.getValue());
				}else {
					propers.append("<input type=hidden name='"+entry.getKey()+"' value='").append(entry.getValue()).append("' />\n");
				}
			}
		}
		String strsign = sign(config, mac.toString());
		System.out.println(strsign);
		propers.append("<input type=hidden name='MAC' value='").append(strsign).append("' />\n");
		form.append(propers);
		form.append("<input type=submit value='确认支付' />\n") ;
		form.append("</form>\n");
	}*/
	private static void send(AppLongPayConfig config,Map<String,Object> map,String url) {
		StringBuilder mac = new StringBuilder();
		StringBuilder propers = new StringBuilder();
		Set<Entry<String,Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			if(entry.getValue() != null && !entry.getValue().toString().equals("")) {
				if(macParam.contains(entry.getKey())) {
					if(mac.length() != 0) {
						mac.append("&");
					}
					mac.append(entry.getKey()+"=").append(entry.getValue());
				}else {
					if(propers.length() == 0) {
						propers.append("?");
					}else {
						propers.append("&");
					}
					propers.append(entry.getKey()+"=").append(entry.getValue());
				}
			}
		}
		if(mac.length() != 0) {
			String strsign = sign(config, mac.substring(0, mac.length()-1));
			log.info(strsign);
			propers.append("MAC").append(strsign);
		}
		log.info(propers.toString());
		
	}
	
	public static void main(String[] args) {
		ReqLongPay pay = new ReqLongPay();
		pay.setAmount(new BigDecimal(12));
		pay.setOrderId(12L);
		pay.setUserId(13L);
		AppLongPayConfig config = new AppLongPayConfig();
		config.setBranchId("0000001");
		config.setMerchantId("0000002");
		config.setPosId("111111111");
		config.setUrl("https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain");
		create(pay, config);
		
	}
	
	private static String sign(AppLongPayConfig config,String mac) {
		RSASig sig = new RSASig();
		sig.setPrivateKey("30820277020100300d06092a864886f70d0101010500048202613082025d020100028181009ba4951169c5deecf03a8ddb2fd934f53747c03a211f63bccc84773182bdd8f7159634705041087e4c9053df05326952a143e1aab5e8ba75ed891a91c2db484b66a064abba6605418944d8763814ff23c161101948ec9ef2dfac735b4bb7c7dac18fbf87157b424780eb7080a3e7c9e79dd4841e44a001edfe497b9e3d2181b9020111028181008954fc004e452e1c5b7ef5a348563dc94ee4f4e7ff1bb25b4b0b783abea783345e575b7228b1da51529d772e31c311a342ffa90009eb7758fec4449ebafdb84126d1d2443dbcec07d9807638ef32cb91bf18eaaa46f6db84de5eba05edfe70ad029449a4cb4de7a95f5c903d6a3fa301f1cc0fe3e29ac72eeab68737f3b2f57d024100d428be0e1463c6b25cc493f23777135a9251b8092f3439c9604d61df8aadb958b947222fd60a489e5de44c379e806015edb0b15030a22cbc5e0ff693fd5bedcf024100bbce1eb6b55f5530f1bb7a437a0f0512f0153d0ada5c5b4ea57c3ea83bd89fe0166d5af1d07f153e83c05eae1585b113c03c8d989bb4d151c96aa78691fac1f7024100bb33020c6c5809ac6ff8bec6a9691113ae481adaed6a511b18bcbfc53e20d0b7b28a0f1b26454f2252d87f7c5ead81f53b236f46c180095ae9959d556714e0e3024100b0c1feca141d7d5b3ddda03f81f004c6879b84beeba237d18cb12be9a1bcd2b4c9d055984bc2e6d16cf14a0d416ec4c74b8449081a1397d48155526089647a51024100bcfe9b05b25578d5d96f80229e015aa58a0af5b0c0aa3ad695fe0d270c4818a737a7abc2f59cf1ea22c7155e06b7d26fba2594e29cb7fd02bd9b6e24b49e425a");
		String strsign = sig.generateSigature(mac.toString());
		log.info(strsign);
		sig.setPublicKey("30819d300d06092a864886f70d010101050003818b00308187028181009ba4951169c5deecf03a8ddb2fd934f53747c03a211f63bccc84773182bdd8f7159634705041087e4c9053df05326952a143e1aab5e8ba75ed891a91c2db484b66a064abba6605418944d8763814ff23c161101948ec9ef2dfac735b4bb7c7dac18fbf87157b424780eb7080a3e7c9e79dd4841e44a001edfe497b9e3d2181b9020111");
		if(sig.verifySigature(strsign, mac)) {
			log.info("【sign】true");
			return strsign;
		}else {
			log.info("【sign】false");
			return null;
		}
	}


	public static void callbackMsg(Map<String, Object> map) {
		StringBuffer mac = new StringBuffer();
		for (Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue() != null && !entry.getValue().toString().equals("")) {
				if(macParam.contains(entry.getValue())) {
					if(mac.length() != 0) {
						mac.append("&");
					}
					mac.append(entry.getKey()+"=").append(entry.getValue());
				}
			}
		}
		
	}
}
