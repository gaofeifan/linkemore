package cn.linkmore.third.pay.wxmini;

import cn.linkmore.third.config.BaseConfig;
import cn.linkmore.third.config.WechatMiniConfig;
import cn.linkmore.util.SpringUtil;

public class Constant { 
	public static String BODY_ORDER = "凌猫停车-订单消费"; 
	public static String APPID = null;
	public static String MCHID = null;
	public static String KEY = null; 
	public static  String CALLBACK = null; 
	static {
		try { 
			WechatMiniConfig payConfig = SpringUtil.getBean(WechatMiniConfig.class);
			APPID = payConfig.getAppId();
			MCHID = payConfig.getMchid();
			KEY = payConfig.getKey();
			BaseConfig baseConfig = SpringUtil.getBean(BaseConfig.class);
			CALLBACK  = baseConfig.getServiceUrl()+"/order/app/callback/v2.0/wechat-mini/order"; 
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	/*
	 * 微信secrect
	 */
	public static String SECRET = "";

	/*
	 * 微信公众号后台 服务器配置处Token
	 */
	public static String TOKEN = "";

	/*
	 * 支付IP地址
	 */
	public static final String SPBILL_CREATE_IP = "127.0.0.1";
	/*
	 * 加密方式
	 */
	public static final String[] SIGNTYPE = { "MD5", "SHA1" };
	/*
	 * 设备号
	 */
	public static final String DEVICE_INFO = "";

	/*
	 * 交易类型(微信网页支付)
	 */
	public static final String TRADE_TYPE_JS = "JSAPI";
	/*
	 * 编码
	 */
	public static final String INPUT_CHARSET = "UTF-8";
	/*
	 * 统一下单接口
	 */
	public static final String PREPAY_ID_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/*
	 * 微信支付查询订单接口
	 */
	public static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	/*
	 * 微信关闭订单接口
	 */
	public static final String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	/*
	 * 微信订单退款接口
	 */
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/*
	 * 微信订单查询退款接口
	 */
	public static final String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	
}
