package cn.linkmore.third.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.third.pay.PayConstants;
import cn.linkmore.util.JsonUtil;

public class Alipay {

	/**
	 * <p><b>支付接口</b></p>
	 * 
	 * @param orderNo 订单编号
	 * @param amount 支付金额
	 * @param bodyOrder 支付内容
	 * @return
	 */
	public static Map<String, String> orderPay(String orderNo,Double amount, String bodyOrder) {
		try {
			return pay(new BizContent(orderNo,new java.text.DecimalFormat("0.00").format(amount),bodyOrder));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	public static Map<String, String> rechargePay(String orderNo,Double amount, String bodyOrder) {
		try {
			return rechargePay(new BizContent(orderNo,new java.text.DecimalFormat("0.00").format(amount),bodyOrder));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * <p><b>支付接口<p><b>
	 * 
	 * 自定义bizContent 
	 * @param bizContent
	 * @return
	 */
	public static Map<String, String> pay(BizContent bizContent) {
		boolean rsa2 = !TextUtils.isBlank(PayConstants.RSA2_PRIVATE);
		
		Map<String, String> initParams =OrderInfoUtil.buildInitOrderParamMap(); 
		initParams.put("biz_content", JsonUtil.toJson(bizContent));
		String orderParam = OrderInfoUtil.buildOrderParam(initParams);
		String privateKey = rsa2 ? PayConstants.RSA2_PRIVATE : PayConstants.RSA_PRIVATE;
		String sign = OrderInfoUtil.getSign(initParams, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		initParams.put("orderInfo", orderInfo);
		return initParams;
	}
	
	public static Map<String, String> rechargePay(BizContent bizContent) {
		boolean rsa2 = !TextUtils.isBlank(PayConstants.RSA2_PRIVATE);
		
		Map<String, String> initParams =OrderInfoUtil.buildInitRechargeParamMap();
		initParams.put("biz_content", JsonUtil.toJson(bizContent));
		
		String orderParam = OrderInfoUtil.buildOrderParam(initParams);
		String privateKey = rsa2 ? PayConstants.RSA2_PRIVATE : PayConstants.RSA_PRIVATE;
		String sign = OrderInfoUtil.getSign(initParams, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		initParams.put("orderInfo", orderInfo);
		return initParams;
	}
	
	/**
	 * <p><b>APP同步通知参数校验</b></p>
	 * 
	 * 0.签名验证是否正确
	 * 1.校验订单号和金额
	 * 2.校验商户ID
	 * 3.响应消息是否为Success
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> checkPayResult(AppPayResult bean,OrderTrade ot){
		
		String result = bean.getResult();
		System.out.println("result>>:"+result); 
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String,Object> map = mapper.readValue(result, Map.class);
			String signType = (String)map.get("sign_type");
			String sign = (String)map.get("sign");
			
			Map<String,String> respMap = (Map<String,String>)map.get("alipay_trade_app_pay_response");
			//第四步： 验证签名是否合法：
			Map<String,String> m =new HashMap<>();
			m.put("sign", sign);
			m.put("sign_type", signType);
			m.put("alipay_trade_app_pay_response", mapper.writeValueAsString(respMap));

			boolean localSign =OrderInfoUtil.getSyncSign(m); 
			if(localSign){ 
				
				//校验参数的合法性
				/**
				 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
				 */
				String sellerId = respMap.get("seller_id");
				String appId = respMap.get("app_id");
				String msg = respMap.get("msg");
				respMap.put("status", "0"); 
				//校验订单号和金额
				boolean orderCheckStatus = ot.checkOrder(respMap);  
				if(orderCheckStatus){
					//校验商户ID
					boolean partnerCheckStatus =  PayConstants.PARTNER.equals(sellerId); 
					if(partnerCheckStatus){
						//校验APPID
						boolean appIdCheckStatus = PayConstants.ALI_APPID.equals(appId); 
						if(appIdCheckStatus){
							boolean successStatus = null!=msg && "success".equalsIgnoreCase(msg); 
							if(successStatus)
								respMap.put("status", "1");
						} 
					} 	
				} 
			}else
			{
				System.out.println("签名不合法 信息已经被篡改！！！！！！！！！");
			}
			return respMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p><b>支付宝 异步通知 参数校验</b></p>
	 * 
	 * 第一步： 在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。
	 * 第二步： 将剩下参数进行url_decode, 然后进行字典排序，组成字符串，得到待签名字符串：
	 * 第三步： 将签名参数（sign）使用base64解码为字节码串。
	 * 第四步： 使用RSA的验签方法，通过签名字符串、签名参数（经过base64解码）及支付宝公钥验证签名。
	 * 第五步：在步骤四验证签名正确后，必须再严格按照如下描述校验通知数据的正确性。
	 * 
	 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	 * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
	 * 4、验证app_id是否为该商户本身。
	 * 上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
	 * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
	 * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
	 * @throws AlipayApiException 
	 * 
	 */
	public static Map<String, String> verifyAsync(Map<String, String> reqMap,OrderTrade or) throws AlipayApiException { 
		 
		//1.
		String signType = reqMap.get("sign_type");
		//4.
		boolean signVerified  = false;
		if(PayConstants.RSA.equals(signType)){
			
			signVerified  = AlipaySignature.rsaCheckV1(reqMap, PayConstants.PUBKEY,PayConstants.CHARSET,signType);
		}else
		{
			signVerified  = AlipaySignature.rsaCheckV2(reqMap, PayConstants.PUBKEY,PayConstants.CHARSET,signType);
		} 
		reqMap.put("status", "0");
			if(signVerified)
			{
				String sellerId = reqMap.get("seller_id");
				String appId = reqMap.get("app_id");
				String tradeStatus = reqMap.get("trade_status"); 
				boolean  orderCheckStatus =or.checkOrder(reqMap);//校验订单号和金额 
				if(orderCheckStatus){
					boolean partnerStatus = PayConstants.PARTNER.equals(sellerId); 
					if(partnerStatus){ 
						boolean appIdCheckStatus = PayConstants.ALI_APPID.equals(appId); 
						if(appIdCheckStatus){
							boolean successStatus = (null!=tradeStatus && 
									("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)
									||"TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)) ); 
							if(successStatus){
								reqMap.put("status", "1");
							} 
						}
							
					}
						
				} 
			}
			else
			{
				System.out.println("参数已经被篡改！reqMap:"+reqMap);
			}
			
		return reqMap;
	}

}
