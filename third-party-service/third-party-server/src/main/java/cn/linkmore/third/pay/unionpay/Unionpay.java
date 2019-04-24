package cn.linkmore.third.pay.unionpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cn.linkmore.third.config.UnionPayConfig;
import cn.linkmore.third.pay.PayConstants;
import cn.linkmore.third.request.ReqApplePay; 

/**
 * 银联支付
 * @author liwenlong
 * @version 1.0
 *
 */ 
public class Unionpay {  
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String create(ReqApplePay rup,  UnionPayConfig unionpayConfig){  
		SDKConfig.init(unionpayConfig); 
		String path = "/gateway/api/appTransReq.do";
		Map<String, String> map = new HashMap<String, String>(); 
		map.put("version", "5.1.0");
		map.put("encoding", "utf-8");
		map.put("signMethod", "01");
		map.put("txnType", "01");//可以按照规范修改应答码
		map.put("channelType", "08"); 
		map.put("txnSubType", "01");
		//此处不一致 旧的为008002
		//map.put("bizType", "000802");
		map.put("bizType", "000201");
		map.put("accessType", "0");
		map.put("merId", unionpayConfig.getMerId());
		map.put("backUrl", PayConstants.getOrderAsyncApplePayUrl());
		map.put("orderId", rup.getNumber());
		map.put("currencyCode", "156");
		map.put("txnAmt",new Long(new Double(rup.getAmount().doubleValue()*100).longValue()).toString());
		map.put("txnTime", sdf.format(rup.getTimestramp()));
		map.put("payTimeout", sdf.format(new Date(rup.getTimestramp()+1000l*60*60)));
		Map<String, String> reqData = AcpService.sign(map,"UTF-8");
		Map<String,String> respData =  AcpService.post(reqData, unionpayConfig.getUnionServiceUrl()+path, "UTF-8"); 
		String tn = null;
		if(respData!=null){
			tn = respData.get("tn");
		}
		/*if(!respData.isEmpty()){
			if(AcpService.validate(respData,"UTF-8")){
				LogUtil.writeLog("验证签名成功");
				String respCode = respData.get("respCode") ;
				if(("00").equals(respCode)){
					tn = respData.get("tn");
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}*/
		
		return tn;
	} 
//	
//	public static void main(String[] args){
//		UnionPayConfig config = new UnionPayConfig();
//		config.setCertDir("");
//		config.setCertPath("cert/acp_test_sign.pfx");
//		config.setCertPwd("000000");
//		config.setCertType("PKCS12");
//		config.setEncryptCertPath("cert/acp_test_enc.cer");
//		config.setLocalServiceUrl("http://app.linkmoreparking.cn");
//		config.setMerId("852331048169991");
//		config.setMiddleCertPath("cert/acp_test_middle.cer");
//		config.setOnline(false);
//		config.setUnionServiceUrl("https://gateway.test.95516.com");
//		config.setRootCertPath("cert/acp_test_root.cer"); 
////		ReqApplePay rup = new ReqApplePay("2018030910000004",1.5D,new Date().getTime()); 
////		create(rup,config);
//		
//	}
	
	public static boolean query(ReqApplePay rup,UnionPayConfig unionPayConfig){ 
		SDKConfig.init(unionPayConfig);
		String path = "/gateway/api/queryTrans.do";
		Map<String, String> map = new HashMap<String, String>(); 
		map.put("version", "5.1.0");
		map.put("encoding", "utf-8");
		map.put("txnType", "00");
		map.put("signMethod", "01");  
		map.put("txnSubType", "00");
		map.put("bizType", "000000");
		map.put("accessType", "0");
		map.put("merId", unionPayConfig.getMerId()); 
		map.put("orderId", rup.getNumber()); 
		map.put("txnTime", sdf.format(new Date(rup.getTimestramp()))); 
		Map<String, String> reqData = AcpService.sign(map,"UTF-8"); 
		Map<String,String> respData =   AcpService.post(reqData, unionPayConfig.getUnionServiceUrl()+path, "UTF-8");  
		boolean payStatus = false;
		if(AcpService.validate(respData,"UTF-8")){
			String code = respData.get("origRespCode");
			payStatus = "00".equals(code);
		}
		return payStatus;
	} 
}
