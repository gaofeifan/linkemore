package cn.linkmore.third.trade.zfb;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;


public class AliMobilePay {
	
	public static  String  oauth2GetOpenid(String appID, String code,String privateKey,String publicKey) {
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				appID,privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(code);
		request.setGrantType("authorization_code");
		AlipaySystemOauthTokenResponse oauthTokenResponse = null;
		try {
			oauthTokenResponse = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(oauthTokenResponse == null) {
			return "error";
		}
		String buyerid = oauthTokenResponse.getUserId();
	   return buyerid;
	}
	
	
	
	
}
