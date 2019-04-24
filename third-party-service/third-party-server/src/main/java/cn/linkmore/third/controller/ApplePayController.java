package cn.linkmore.third.controller;

import java.security.MessageDigest;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.config.UnionPayConfig;
import cn.linkmore.third.pay.unionpay.LogUtil;
import cn.linkmore.third.pay.unionpay.SDKConfig;
import cn.linkmore.third.pay.unionpay.SDKUtil;
import cn.linkmore.third.request.ReqApplePay;
import cn.linkmore.third.service.ApplePayService;
import cn.linkmore.util.JsonUtil;
/**
 * Controller - 银联苹果支付
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/apple-pay")
public class ApplePayController {
	
	@Autowired
	private UnionPayConfig unionPayConfig;
	
	@Autowired
	private ApplePayService applePayService;
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqApplePay order) {
		return this.applePayService.order(order);
	}
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json) {
		Map<String,String> param = JsonUtil.toObject(json,HashMap.class);
		Boolean flag = false; 
		SDKConfig.init(unionPayConfig);
//		log.info("config:{}",JsonUtil.toJson(unionPayConfig));
//		log.info("json:{}",json);
//		log.info("sdk:{}",JsonUtil.toJson(SDKConfig.getConfig()));
//		AcpService.validate(param,"UTF-8");
		if("00".equals(param.get("respCode"))&&unionPayConfig.getMerId().equals(param.get("merId"))) {
			flag = true;
		}
		return flag;
	}
	
	private boolean validateAppResponse(String jsonData, String encoding) {
		if (SDKUtil.isEmpty(encoding)) {
			encoding = "UTF-8";
		}

        Pattern p = Pattern.compile("\\s*\"sign\"\\s*:\\s*\"([^\"]*)\"\\s*");
		Matcher m = p.matcher(jsonData);
		if(!m.find()) {
			LogUtil.writeErrorLog("内容不正确。");
			return false;
		}
		String sign = m.group(1);

		p = Pattern.compile("\\s*\"data\"\\s*:\\s*\"([^\"]*)\"\\s*");
		m = p.matcher(jsonData);
		if(!m.find()) {
			LogUtil.writeErrorLog("内容不正确。");
			return false;
		}
		String data = m.group(1);

		try {
			MessageDigest md = null;
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(data.getBytes(encoding));
			byte[] bs = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : bs) { 
			     String hex = Integer.toHexString(b & 0xFF); 
			     if (hex.length() == 1) { 
			       hex = '0' + hex; 
			     }
			     sb.append(hex);
			}
			Signature st = Signature.getInstance("SHA1withRSA", "BC");
			//st.initVerify(this.appVerifyPubKey);
			st.update(sb.toString().toLowerCase().getBytes(encoding));
			return st.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} 
		return false;
	}
}
