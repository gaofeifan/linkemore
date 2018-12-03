package cn.linkmore.third.trade.wx;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	/**
	 * 
	 * @param deviceInfo 门店信息
	 * @param attach 附加信息，用于查询
	 * @param outTradeNo 商户订单号
	 * @param totalFee 订单金额
	 * @param body 商品描述
	 * @param timeStart 交易起始时间
	 * @param timeExpire 交易结束时间
	 * @param tradeType 交易类型，扫码，公众号或者APP
	 * @param productId 商品ID编码
	 * @return
	 */
	public String sendWxUniPayRequest(String deviceInfo, String attach, String outTradeNo, BigDecimal totalFee,  
            String body, String timeStart, String timeExpire, String tradeType, String productId, String openid,String appID,String mchID,String key){
		String strResponse = null;
		/*try {
			String requestXml = (String) UniPayReqData.getUniPayReq(deviceInfo, attach, 
					outTradeNo, totalFee, body, timeStart, 
					timeExpire, tradeType, productId, openid,appID,mchID,key);
			System.out.println(requestXml);
			strResponse = sendPost(requestXml);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return strResponse;
	
	}
	
	public String sendGetRequest() {
		String response = null;
		/*try {
			response = sendGet();
			return response;
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return response;
	}

}
