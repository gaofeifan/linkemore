package cn.linkmore.third.trade.wx;

import java.util.Map;
import com.alibaba.fastjson.JSON;
import cn.linkmore.third.pay.wxmini.HttpTool;

/**
 * 获取网页Token
 */
public class WeChatPay {
	
	public static  final String GetTokenRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	public static  String  oauth2GetOpenid(String appID, String code,String secret) {
		String url =String.format(GetTokenRequest, appID,secret,code);
		System.out.println(url);
		String res = HttpTool.sendGet(url, "UTF-8");
		System.out.println(res);
		Map<String, Object> map = (Map<String, Object>) JSON.parse(res);
		return String.valueOf(map.get("openid"));
	}
	

}
