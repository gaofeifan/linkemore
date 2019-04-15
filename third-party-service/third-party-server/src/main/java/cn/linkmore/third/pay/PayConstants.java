package cn.linkmore.third.pay;

import cn.linkmore.third.config.BaseConfig;
import cn.linkmore.util.SpringUtil;

public class PayConstants {
	
	public static final String BODY_ORDER = "凌猫停车-订单消费";
	
	// ****************************************微信支付*********************************************************************************************************
	//微信appid 
	//public static String WX_APPID = "wx1fa04d7b1642f8f3";
	
	public static String WX_APPID = "wx32565e31de1d0b5e";
	
	public static String server = null;

	//商户号 
	//public static final String MCHID = "1492790062";
	public static final String MCHID = "1524989821";

	//微信支付密钥 key
	//public static String KEY = "scqbOHIaWxcrVisrZHVQy6KjLd3WjkC2";
	public static String KEY = "linkmoretech20151229LMTC20190313";


	//支付IP地址
	public static final String SPBILL_CREATE_IP = "127.0.0.1";
	
	//加密方式
	public static final String[] SIGNTYPE = { "MD5", "SHA1" };
	
	//设备号
	public static final String DEVICE_INFO = "";
	static {
		try { 
			BaseConfig baseConfig = SpringUtil.getBean(BaseConfig.class);
			server = baseConfig.getServiceUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public static String getOrderAsyncApplePayUrl(){
		return server+"/order/app/callback/v2.0/apple/order";
	}
	 
	public static String getOrderAsyncWechatUrl(){
		return server+"/order/app/callback/v2.0/wechat/order";
	} 
	
	public static String getRechargeAsyncWechatUrl(){
		return server+"/order/app/callback/v2.0/wechat/recharge";
	}  

	//交易类型(微信app支付)
	public static final String TRADE_TYPE_APP = "APP";
	
	//编码
	public static final String INPUT_CHARSET = "UTF-8";
	//统一支付接口
	public static final String PREPAY_ID_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信支付订单查询接口
	public static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//微信订单退款接口
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//微信订单查询退款接口
	public static final String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//微信关闭订单接口
	public static final String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	
	// ****************************************支付宝支付*********************************************************************************************************
	
	
	public static final String METHOD = "alipay.trade.app.pay";
	
	public static final String CHARSET="utf-8";

	public static final String RSA2 = "RSA2";

	public static final String RSA = "RSA";

	public static final String VERSION = "1.0";
	
	
	//啪啪易行
	//public static final String PARTNER ="2088521384003031";//"data@papayixing.com";
	//凌猫智行
	public static final String PARTNER ="2088331964563020";

	/** 支付宝支付业务：入参app_id */
	/**
	 * 啪啪易行正式环境APPID
	 */
	//public static final String ALI_APPID = "2016121904425106";
	
	//凌猫智行正式环境APPID
	public static final String ALI_APPID = "2019011563002677";
	/**
	 * 测试账号APPID
	 */
	//	public static final String ALI_APPID = "2016122104482666";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";
	
	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpeTPlkJNP+qyzyjcjLhnqBzierjMd0cKn6a/X+vTDRE71kZ9BHzf1obx+kYHMVTvXkDb7k3fzYIUki/Cp3Zax70Az7UZp9rG/DU/08EWhkf7PbQ7o6eMhwzL4gktXLqnSwZ5ivCWieguf5CZXtc4gdCVOkDfWC3m3qAv4+O+CBFdsweFvcPCzWPWgufWBMn3nyiVnJFMgzTZc1JX+3TjEvjeE6p/3vBI5TAb3KAUTbeDP38V8WG4C0BNH2Mkj7xSYeatuMXNqrXjkZt+clP5EhQAIGwI9I8qQBFxTgT98I6ST6Ovip6597rt/8giw9SVF/AqyHXfBy5ZG7PRlNzDhAgMBAAECggEAIeopT7+gpo2TaMqHF+UqfoRuBR7o7fv6esnHcWhTBcp2bdWpfJw8VMIZz4jzgesftkCMvB6/3eq3bIenfgViTpaijOh6wgnD6CzW4I3Gj6HVkewVrl/LTqw8Sz0iFJgCs0rM5yrNjLyN07wglb0jJDO6956bfcxM8hDHLHN81zXjJI6CZDj1I3SUfp0Ecog+VhYgN2Y+mErj/9JSxYz2e6CSB50ifrJnA+UAVz3l95a4ZqMCO9ZUnXxi1bv98cPzVHOFDpUa1ti2cuFd/kIxPVpKloMjs71w63a2kDnJ/fzEdfFgu/ZiOh72B8N5Vx8ItWuQ3eFFJzPoRtE3nrZpAQKBgQD/fg3c2HQp1X3bUecY6xiKfEK/yFkVW/oDPcXn4KjMpFX7otF4ffoUxadPC1WqHCQyVKTEnqAH7LuXrP+fPR+Abi6/ak8L9FPbIQZji5zrENKXzwgGc+7diyHRL1J1T9UYW0dymgSIV5RWL8ngS+utkFJPimc/S6Hs3pcog8BpNQKBgQDp7/MUAWJ+rkRM/eVHuzEpPxfKy8vwywYRtC9PQDOrJIUfslzcuU2rGrA4quam+RkQODwOPCU9ijidiat1QYsIG3rav6Gc7CTEmrkX095F++Jhv1+5V5bl5UVybi05ZjWG/D0LpBbvfoXY3r63YjrzgKCG1zJ7WmIJ7QcaMEDKfQKBgE0Kg7+efZjHycXCsr7u1V2dDrup/ELWk1M641yCgCQFQfA5LpT419o/p63MKy66gaezomOvuW3qUr7v6bzov4EIuIv70I44RLkFAlouFUlEr1wbj73yY4rmk3HypRi+0/EeRTMngkpirlY23tcFzbFunYNiprONaOx2SnprEDApAoGAVa4HZmVXcLRc2aIIDqyVxdiQ2zqnGCsTrvpCdZE7G0yF4WUPM9w+gROGRUr6rV8AeftOq5wFu7NbJv/cJ1b5F+AQr/uNso6erM9KSVrUxucJM+nGonhdERhWxiFqOOKhai4KIbBYoiDB3vs9+KZmlvaeVKSOnbUtwRrqBgJ0SHUCgYEApSi8wcljoWf9LQyqGXGq6lM8y6hfdnKOZLCFYr9wbmTmLFKrOuQBpYPP5v9VvHeErT3JY/6SmTPy9Zm88utpMCIfyN8Wju6lT248iaOQb1HxNA7oqSnNcXdrJHh4jQ1BPig8DpKwuHfrsWcZHdkE9x5bQHu/JDbEWXHGtNCs38M=";
	//凌猫科技
	//public static final String RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCjSzSR972gTfhws9qElyyM9c9uLy1A8MLspcC1ncm4gd6n8LPUSf5DEvbWtxsfUTsiZ5H22hhXElHTeUuUdxzhFu1F/WtrxT6TcAJDr8TeHTJQLDw5AOkPKdQQc2uQHGwEbJuFroxldKO/KfB1J5hRBOpvDRmwT1QU7QTU5vbJtTvErtTWitgnSpSP7Ijd0yjUIHxkDEp4+Vp91Cih+q9IiiJ6DDaa5Qt8ovkNLB+sg4uiZhV+0Rt9Atd7s28SlZ9cUnH3qVVDEo+ATpPtEIEGTa0noHnksOlWNUwpdgwkyjvni3K7csdYv/nMpmLVlwwc11Et1VFqeMF/9lhP1jvNAgMBAAECggEBAIya4i5aAwb4fFcXFq6auTq7Ihx/NNk5Nq6sXs5DioF8GivDgCTofa5lsvPZgNoriN6sDaxfyRuYY6rTQ7gYBF7w7egZORj4I1Sy2tBLlhmnTD5qiISQ+x0aaEauC81wG7aHijbGwI9Pqvuc8jb+nBIPSInM3vTIqd7G5CUqpw+wxRf5ciavXcXI0JNcvwJd8BbtbvjdlVzsqE6xsvuJ7+FjFZdLE3v8GqTE8+LUde+4UzwvneJPUC65R7yWIX2JDOoQl9B7MMRgFNxWLVoLBq9YAhR+BZqRp/68bC8TOoOQQyGxrz/RIqwIcsMeVk+T2RUr40CjHGMeEy8QeLAUYgUCgYEA0B+tn+Uz2wW/KU4yrEghtQYNsvgDCITDq9GHbP1UfwMNcHQE1zgBacKrJD/42sN8MfETLAuIgakOgYHUtUsTdcUcsmz9UZoMUf6SB/Cm8WwIwJFGxrD+wuhFsHOUTcJxYe4BUKV9KQHXsBhLkooV0RZElYlGn+tMu8aYG/4cQMcCgYEAyNuCK5VT8CyPpmAza57ZHO+WNu/HTgTN3iC/Wp/Lo96RPvQC4xJpzivWHzqFBKnQ1KuzA1qpvTCde3GYAEhlvTn3+++a1DAG9LnA0lZVWh2Ng/pB7/PGdknXcU4wWF40zRmvp6gXqIXv1myyDat5Wijg4IWz8nQ9zLHoOCb4MssCgYEApBvmj4dvDUyJwOtNX7X9Bj4AvVd9JNFzbV9p1xIm94QTq9GAI6igzKFYy9u5wd6VZtf+UyPA/uSscIhMjqvla18DUtpIIXlEL795rNMhAIs8l4O2eGQGbvAyS4HHtk0VlK/Uz5++goBWcB+oL6O8skNJz1QQoXcr6YEeXixQYO8CgYB4ipwFUfntAqE3RTa22L8NyY+J6r7eyK1QKLEOTyYkczcR49kzs2JCNDatyS35TXyhlWW6lpeVje6FHfgN+2TrejWkQfjWbPhPuIbK9LsGeQPr963ItrEdXlVoj3ceIXNov4iWgp+oKLF2Kwd0grsya6QButvynuZTyVVHFcQQnwKBgQDI+c3MFA9F98hVeOksBb+mlf5QmETUDKGlokMGbbP49/JNlNqru5pVUqRmOumQiCv+9VvjfRf8VHjSmRYBcZKPJ7o7EALAU2AV8oJJMstQ8d5fFXkrV/EzQc8teInRxdnyc4ayvA92elOiHqPA15C7wK1Xa+nbOAIlYHsuioXfyQ==";
	
	//凌猫智行RAS_PRIVATE_LM
	public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpeTPlkJNP+qyzyjcjLhnqBzierjMd0cKn6a/X+vTDRE71kZ9BHzf1obx+kYHMVTvXkDb7k3fzYIUki/Cp3Zax70Az7UZp9rG/DU/08EWhkf7PbQ7o6eMhwzL4gktXLqnSwZ5ivCWieguf5CZXtc4gdCVOkDfWC3m3qAv4+O+CBFdsweFvcPCzWPWgufWBMn3nyiVnJFMgzTZc1JX+3TjEvjeE6p/3vBI5TAb3KAUTbeDP38V8WG4C0BNH2Mkj7xSYeatuMXNqrXjkZt+clP5EhQAIGwI9I8qQBFxTgT98I6ST6Ovip6597rt/8giw9SVF/AqyHXfBy5ZG7PRlNzDhAgMBAAECggEAIeopT7+gpo2TaMqHF+UqfoRuBR7o7fv6esnHcWhTBcp2bdWpfJw8VMIZz4jzgesftkCMvB6/3eq3bIenfgViTpaijOh6wgnD6CzW4I3Gj6HVkewVrl/LTqw8Sz0iFJgCs0rM5yrNjLyN07wglb0jJDO6956bfcxM8hDHLHN81zXjJI6CZDj1I3SUfp0Ecog+VhYgN2Y+mErj/9JSxYz2e6CSB50ifrJnA+UAVz3l95a4ZqMCO9ZUnXxi1bv98cPzVHOFDpUa1ti2cuFd/kIxPVpKloMjs71w63a2kDnJ/fzEdfFgu/ZiOh72B8N5Vx8ItWuQ3eFFJzPoRtE3nrZpAQKBgQD/fg3c2HQp1X3bUecY6xiKfEK/yFkVW/oDPcXn4KjMpFX7otF4ffoUxadPC1WqHCQyVKTEnqAH7LuXrP+fPR+Abi6/ak8L9FPbIQZji5zrENKXzwgGc+7diyHRL1J1T9UYW0dymgSIV5RWL8ngS+utkFJPimc/S6Hs3pcog8BpNQKBgQDp7/MUAWJ+rkRM/eVHuzEpPxfKy8vwywYRtC9PQDOrJIUfslzcuU2rGrA4quam+RkQODwOPCU9ijidiat1QYsIG3rav6Gc7CTEmrkX095F++Jhv1+5V5bl5UVybi05ZjWG/D0LpBbvfoXY3r63YjrzgKCG1zJ7WmIJ7QcaMEDKfQKBgE0Kg7+efZjHycXCsr7u1V2dDrup/ELWk1M641yCgCQFQfA5LpT419o/p63MKy66gaezomOvuW3qUr7v6bzov4EIuIv70I44RLkFAlouFUlEr1wbj73yY4rmk3HypRi+0/EeRTMngkpirlY23tcFzbFunYNiprONaOx2SnprEDApAoGAVa4HZmVXcLRc2aIIDqyVxdiQ2zqnGCsTrvpCdZE7G0yF4WUPM9w+gROGRUr6rV8AeftOq5wFu7NbJv/cJ1b5F+AQr/uNso6erM9KSVrUxucJM+nGonhdERhWxiFqOOKhai4KIbBYoiDB3vs9+KZmlvaeVKSOnbUtwRrqBgJ0SHUCgYEApSi8wcljoWf9LQyqGXGq6lM8y6hfdnKOZLCFYr9wbmTmLFKrOuQBpYPP5v9VvHeErT3JY/6SmTPy9Zm88utpMCIfyN8Wju6lT248iaOQb1HxNA7oqSnNcXdrJHh4jQ1BPig8DpKwuHfrsWcZHdkE9x5bQHu/JDbEWXHGtNCs38M=";
	
	//凌猫科技应用公钥
	//public static final String RSA_PUBLIC ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo0s0kfe9oE34cLPahJcsjPXPbi8tQPDC7KXAtZ3JuIHep/Cz1En+QxL21rcbH1E7ImeR9toYVxJR03lLlHcc4RbtRf1ra8U+k3ACQ6/E3h0yUCw8OQDpDynUEHNrkBxsBGybha6MZXSjvynwdSeYUQTqbw0ZsE9UFO0E1Ob2ybU7xK7U1orYJ0qUj+yI3dMo1CB8ZAxKePlafdQoofqvSIoiegw2muULfKL5DSwfrIOLomYVftEbfQLXe7NvEpWfXFJx96lVQxKPgE6T7RCBBk2tJ6B55LDpVjVMKXYMJMo754tyu3LHWL/5zKZi1ZcMHNdRLdVRanjBf/ZYT9Y7zQIDAQAB" ;
	//凌猫智行应用公钥RSA_PUBLIC_LM
	public static final String RSA_PUBLIC ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Xkz5ZCTT/qss8o3Iy4Z6gc4nq4zHdHCp+mv1/r0w0RO9ZGfQR839aG8fpGBzFU715A2+5N382CFJIvwqd2Wse9AM+1Gafaxvw1P9PBFoZH+z20O6OnjIcMy+IJLVy6p0sGeYrwlonoLn+QmV7XOIHQlTpA31gt5t6gL+PjvggRXbMHhb3Dws1j1oLn1gTJ958olZyRTIM02XNSV/t04xL43hOqf97wSOUwG9ygFE23gz9/FfFhuAtATR9jJI+8UmHmrbjFzaq145GbfnJT+RIUACBsCPSPKkARcU4E/fCOkk+jr4qeufe67f/IIsPUlRfwKsh13wcuWRuz0ZTcw4QIDAQAB";
	
	public static final String FORMATER = "json";

	//凌猫科技 支付宝公钥
	//public static final String PUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	//凌猫智行 支付宝公钥RSA(SHA256)
	public static final String PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkxTDKi1U+xDbQGbAJVjAdIAYdFr03KEmUzA+KFyfFNiP4m0g3KTXWdxV7Zsl+9jDN/fO3zsb2U/Ypk6nqJeeH2M3AW/8PGgwDhOdkzvG11yNfFfh6m61xgbPZh/3zNy7bsmcgtgWIteRM1DARQUXoJS1DAR7HDiu9cyxVaJYVmexYVmFhBFBbXLBfcABMHZSEQNaUiamGOqM/KXqcWpa/e+P25KCMJWAoA23FwXgA1nKLyQGO/joz+i4FGRnja1wkYBNbG3AmWGy4Fak4lH5xvvX6kYvp2lm0PSVrmIurHq6OJppVZgu7DVNJ6zV+vLZ+EHMyU+lxJvUTOIO5uR3WwIDAQAB";
	
	//支付宝
	public static final String GATEWAY ="https://openapi.alipay.com/gateway.do";

	/**
	 * 支付宝异步回调
	 * @return
	 */
	public static String getOrderAsyncAlipayUrl(){
		return server +"/order/app/callback/v2.0/alipay/order";
	}
	
	public static String getRechargeAsyncAlipayUrl(){
		return server+"/order/app/callback/v2.0/alipay/recharge";
	} 
}
