package cn.linkmore.third.trade.zfb;

public class AlipayConfig {

	
	
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "-2088521384003031";
	
	public static String service = "alipay.wap.create.direct.pay.by.user";
	
		// 字符编码格式 目前支持utf-8
		public static String input_charset = "utf-8";
			
		// 支付类型 ，无需修改
		public static String payment_type = "1";
		
		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "";
		
		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问,内网的话必须同一网段，如192.168.1.21
		//public static String return_url =  "http://test.linkmoreparking.cn/api/order/h5/f";
		
		public static String return_url =  "http://api.linkmoreparking.com/api/order/h5/f";
		
		public static String sign_type = "MD5";
		
		// MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	    public static String key = "u77e1e57sux9brjf6fr9an9xhju8hnqe";
		
	    public static String  key(String key){
	 	   return key;
	    }
	    public static String partner(String partner){
	 	   return partner;
	    }
}
