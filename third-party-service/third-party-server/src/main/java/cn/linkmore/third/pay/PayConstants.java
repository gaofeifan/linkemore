package cn.linkmore.third.pay;

import cn.linkmore.third.config.BaseConfig;
import cn.linkmore.util.SpringUtil;

public class PayConstants {
	
	public static final String BODY_ORDER = "凌猫停车-订单消费";
	
	// ****************************************微信支付*********************************************************************************************************
	//微信appid 
	public static String WX_APPID = "wx1fa04d7b1642f8f3";
	
	public static String server = null;

	//商户号 
	public static final String MCHID = "1492790062";

	//支付密钥 key
	public static String KEY = "scqbOHIaWxcrVisrZHVQy6KjLd3WjkC2";


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
		return server+"/callback/v2.0/apple/order";
	}
	 
	public static String getOrderAsyncWechatUrl(){
		return server+"/callback/v2.0/wechat/order";
	} 
	
	public static String getRechargeAsyncWechatUrl(){
		return server+"/callback/v2.0/wechat/recharge";
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
	
	
	
	public static final String PARTNER ="2088521384003031";//"data@papayixing.com";
	
	/** 支付宝支付业务：入参app_id */
	/**
	 * 正式环境APPID
	 */
	public static final String ALI_APPID = "2016121904425106";
	
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
	public static final String RSA2_PRIVATE = "";
	//public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCPvZ+d4DVIuVwdiEiv7Br8qC43sY/srQVSv8YfRXZa/iDw9ucTZDst3iZU/sP3cc7oqpr+/a5xTOqCYbZEJYw8BkkvdjIx1AB7XRni9EmD0N27UNdYYd9rntFRHvJY3HpkpNSK6Y8hdeMnojC69Xjr6GhBTXmTcuQDoW/B6iYF6sJAEQBA0KRp2RQPjkZg4VZQlAdP9C9GP/nYWtnIZY22TDnloAGLmOBTFVBll4gbrWdRLG1mEhz5V6Ff9/AAQbIItClGNOfpikn4bb33+6RJyAQMwHdKZpaggZUaHkNqU8XMvLXgAC4WEXZ2nCAv67JEPN2RMLQMYwnIEWYUw2ohAgMBAAECggEAYNnFuNyfSncKqCNxxodxz5eKPKB5d2FFWvI4jk2BZ+IfU/0oyZoZfdhXP6HBbPLUFVUOxo0rgs/umxc3dkdObAtYOJCNVaGhCX47uP4coqKTJGleEAEyRxNluiD97f3wweuGAmtIs6T4/3V7HB3V8i5QQN/w8PbSx9d4s9IO+KCom4/CUFYtne3h+wLGhKjuiyabCL13LbfN6eB6BBc/eS0iiaEXVz6hneld+GDM9hOKPG2sa4gJDV/K8itEPe7PCfyhWnuOiIpyAKhukahk8rmIY61z+m9Hg0Zx6qZmHlckVm5Fp6r6OcZnlpacmDRGdAKRBwm4t16ZWU4Lz0zqoQKBgQDJj7evvLsb1tYADsQ0w7CHeSUgQMgfw2Uw78eRPkaCzP6vqXYreKfT1S2080pEdgYkX6Q0xiIJxZ0ziDyBayVXRKLiqGtrnGF9lUNLkb2F3/7VH/0H4FyKqhkSXJBvda4EvXcusmaFwkTiqmqn7MvgsiHZ6IpHG7qTz2q1dY3UJQKBgQC2kBcZBeVVoFxG4x0xsy1tS2uS5Z0cEq54zWgX90c+e3Nu5tz4GkIOYOx9QVsip632IOuYPU92Ibd7bpQNy1hFPdBlRDB2yxh7QK45tlVc2x8d8XLXnd86Sra4yemDvTG1zbwScVg0tkIGJpKvSTrLtrc8UxOgSWtvAOATzpe/TQKBgFIbin87SZlgndJuMX0xDlNRhU8rNJrD1Q/nTYucK8sSyNk0FiBFSVCgsbrZtLJIxd3BXDnnUGxcpNoeME+kHxT6r8PXSdMPZCNtT3mUR/kmM0AMhN3Vhr0x5360WF4fOSFTMR7Dq8HnTnprY4E+lBrXVbTX5qRq5klaICUFWx5FAoGAIZeT2HPgRggy0oxZw9pfYnv9OFNtvEuntOy+2aIThui6Tc7HHBPg0bp96XJMLa1C8kYOc73cUDVWCK1JKtyeymbcYLXSYRlv0MYPCOmG/YYITL8vzYLsH1K+GnR0tlvquLQ5hf87Jr/emDkixoQoAHIHd18l/llkHlG/6YUbIQUCgYA/gS2rTHjuL2PMzbGCHIMptAHE0BwSbSRYfGQuBzMYXObcYfr2lFtKRu/J9SPy9q0Bnv3ihj6uU5Ac+0sO8q+QOjM8C+9RE0F4Vi3PNZALsdBIbIT/d5oAsiIPRAGFZWsgsRVjBlfBvMTug6FMmjLI2loOmYxnYWkWcdRSzNVYEQ==";
	public static final String RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCjSzSR972gTfhws9qElyyM9c9uLy1A8MLspcC1ncm4gd6n8LPUSf5DEvbWtxsfUTsiZ5H22hhXElHTeUuUdxzhFu1F/WtrxT6TcAJDr8TeHTJQLDw5AOkPKdQQc2uQHGwEbJuFroxldKO/KfB1J5hRBOpvDRmwT1QU7QTU5vbJtTvErtTWitgnSpSP7Ijd0yjUIHxkDEp4+Vp91Cih+q9IiiJ6DDaa5Qt8ovkNLB+sg4uiZhV+0Rt9Atd7s28SlZ9cUnH3qVVDEo+ATpPtEIEGTa0noHnksOlWNUwpdgwkyjvni3K7csdYv/nMpmLVlwwc11Et1VFqeMF/9lhP1jvNAgMBAAECggEBAIya4i5aAwb4fFcXFq6auTq7Ihx/NNk5Nq6sXs5DioF8GivDgCTofa5lsvPZgNoriN6sDaxfyRuYY6rTQ7gYBF7w7egZORj4I1Sy2tBLlhmnTD5qiISQ+x0aaEauC81wG7aHijbGwI9Pqvuc8jb+nBIPSInM3vTIqd7G5CUqpw+wxRf5ciavXcXI0JNcvwJd8BbtbvjdlVzsqE6xsvuJ7+FjFZdLE3v8GqTE8+LUde+4UzwvneJPUC65R7yWIX2JDOoQl9B7MMRgFNxWLVoLBq9YAhR+BZqRp/68bC8TOoOQQyGxrz/RIqwIcsMeVk+T2RUr40CjHGMeEy8QeLAUYgUCgYEA0B+tn+Uz2wW/KU4yrEghtQYNsvgDCITDq9GHbP1UfwMNcHQE1zgBacKrJD/42sN8MfETLAuIgakOgYHUtUsTdcUcsmz9UZoMUf6SB/Cm8WwIwJFGxrD+wuhFsHOUTcJxYe4BUKV9KQHXsBhLkooV0RZElYlGn+tMu8aYG/4cQMcCgYEAyNuCK5VT8CyPpmAza57ZHO+WNu/HTgTN3iC/Wp/Lo96RPvQC4xJpzivWHzqFBKnQ1KuzA1qpvTCde3GYAEhlvTn3+++a1DAG9LnA0lZVWh2Ng/pB7/PGdknXcU4wWF40zRmvp6gXqIXv1myyDat5Wijg4IWz8nQ9zLHoOCb4MssCgYEApBvmj4dvDUyJwOtNX7X9Bj4AvVd9JNFzbV9p1xIm94QTq9GAI6igzKFYy9u5wd6VZtf+UyPA/uSscIhMjqvla18DUtpIIXlEL795rNMhAIs8l4O2eGQGbvAyS4HHtk0VlK/Uz5++goBWcB+oL6O8skNJz1QQoXcr6YEeXixQYO8CgYB4ipwFUfntAqE3RTa22L8NyY+J6r7eyK1QKLEOTyYkczcR49kzs2JCNDatyS35TXyhlWW6lpeVje6FHfgN+2TrejWkQfjWbPhPuIbK9LsGeQPr963ItrEdXlVoj3ceIXNov4iWgp+oKLF2Kwd0grsya6QButvynuZTyVVHFcQQnwKBgQDI+c3MFA9F98hVeOksBb+mlf5QmETUDKGlokMGbbP49/JNlNqru5pVUqRmOumQiCv+9VvjfRf8VHjSmRYBcZKPJ7o7EALAU2AV8oJJMstQ8d5fFXkrV/EzQc8teInRxdnyc4ayvA92elOiHqPA15C7wK1Xa+nbOAIlYHsuioXfyQ==";

	//public static final String RSA_PUBLIC ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj72fneA1SLlcHYhIr+wa/KguN7GP7K0FUr/GH0V2Wv4g8PbnE2Q7Ld4mVP7D93HO6Kqa/v2ucUzqgmG2RCWMPAZJL3YyMdQAe10Z4vRJg9Ddu1DXWGHfa57RUR7yWNx6ZKTUiumPIXXjJ6IwuvV46+hoQU15k3LkA6FvweomBerCQBEAQNCkadkUD45GYOFWUJQHT/QvRj/52FrZyGWNtkw55aABi5jgUxVQZZeIG61nUSxtZhIc+VehX/fwAEGyCLQpRjTn6YpJ+G299/ukScgEDMB3SmaWoIGVGh5DalPFzLy14AAuFhF2dpwgL+uyRDzdkTC0DGMJyBFmFMNqIQIDAQAB" ;
	public static final String RSA_PUBLIC ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo0s0kfe9oE34cLPahJcsjPXPbi8tQPDC7KXAtZ3JuIHep/Cz1En+QxL21rcbH1E7ImeR9toYVxJR03lLlHcc4RbtRf1ra8U+k3ACQ6/E3h0yUCw8OQDpDynUEHNrkBxsBGybha6MZXSjvynwdSeYUQTqbw0ZsE9UFO0E1Ob2ybU7xK7U1orYJ0qUj+yI3dMo1CB8ZAxKePlafdQoofqvSIoiegw2muULfKL5DSwfrIOLomYVftEbfQLXe7NvEpWfXFJx96lVQxKPgE6T7RCBBk2tJ6B55LDpVjVMKXYMJMo754tyu3LHWL/5zKZi1ZcMHNdRLdVRanjBf/ZYT9Y7zQIDAQAB" ;
	
	public static final String FORMATER = "json";

	//支付宝公钥
	public static final String PUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	//支付宝
	public static final String GATEWAY ="https://openapi.alipay.com/gateway.do";

	/**
	 * 支付宝异步回调
	 * @return
	 */
	public static String getOrderAsyncAlipayUrl(){
		return server +"/callback/v2.0/alipay/order";
	}
	
	public static String getRechargeAsyncAlipayUrl(){
		return server+"/callback/v2.0/alipay/recharge";
	} 
}
