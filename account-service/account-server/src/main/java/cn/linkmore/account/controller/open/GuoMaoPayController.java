package cn.linkmore.account.controller.open;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everhomes.util.SignatureHelper;
import com.everhomes.util.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import cn.linkmore.account.config.GuoMaoPayConfig;
import cn.linkmore.account.controller.open.requset.OrderPaymentNotificationCommand;
import cn.linkmore.util.HttpUtils;
import io.swagger.annotations.Api;

@Api(tags = "GuoMaoFuWu")
@RestController
@RequestMapping("/open/guomao")
public class GuoMaoPayController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GuoMaoPayConfig payConfig;
	
	public static ObjectMapper mapper; //= new ObjectMapper();
	static {
		mapper= new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	/**
	 * 跳转到国贸服务支付中间页
	 * @param appType app类型 android,ios
	 * @param amount 订单金额(分)
	 * @param successUrl 成功跳转链接
	 */
	@RequestMapping(value = "/payPage")
	public void createOrderPage(String appType,String amount,String successUrl,HttpServletResponse response) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String,String> params=new HashMap<String,String>();
		params.put("amount", amount);
		params.put("payeeUserId", ""+payConfig.getPayeeUserId());
		params.put("bizOrderNum", uuid);
		params.put("appKey", payConfig.getAppKey());
		String signature = SignatureHelper.computeSignature(params, payConfig.getPaySecretKey());
		
		String clientAppName=StringUtils.equalsIgnoreCase(appType, "ios")? "iOS_GuoMaoFuWu":"Android_GuoMaoFuWu";
		String url = payConfig.getBaseUri()+payConfig.getPayHomePath() + "?clientAppName="+clientAppName //APP名称
				+ "&amount=" + amount	//订单金额(分)
				+ "&payeeUserId=" + payConfig.getPayeeUserId() //收款方id
				+ "&bizOrderNum=" + uuid //第三方系统订单号
				+ "&signature=" + signature	//签名
				+ "&appKey=" + payConfig.getAppKey()
				// + "&bizPayerPhone=" + bizPayerPhone   //当前用户手机号(国贸)
				// + "&bizPayerUserId=" + bizPayerUserId //当前用户userId(国贸)
				+ "&callbackUrl=" + urlEncode(payConfig.getCallBackUri()); //后台回调地址

		if(StringUtils.isNotEmpty(successUrl)) {
			url+="&successUrl="+ urlEncode(successUrl); //支付成功的前端页面跳转链接，默认不填返回上一页 
		}
		String html="<script language=\"javascript\" type=\"text/javascript\">window.location.href=\"" + url + "\"</script>";
		try {
			response.setHeader("content-type", "text/html;charset=UTF-8");
			OutputStream out = response.getOutputStream();  
			out.write(html.getBytes("UTF-8")); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付回调(失败不回调)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/callBack")
	public String callBack(OrderPaymentNotificationCommand notifyCmd) {
		try {
			log.info("国贸服务:收到支付回调->{}",mapper.writeValueAsString(notifyCmd));
			Map<String, String> params = new HashMap<>();
			StringHelper.toStringMap(null, notifyCmd, params); //将回调接收的参数转换成map
			params.remove("signature");
			boolean isVerify = SignatureHelper.verifySignature(params, payConfig.getCallBackSecretKey(),notifyCmd.getSignature()); //回调参数签名验证
			log.info("国贸服务:支付回调验签->{}",isVerify);
			if(isVerify) {
				//修改订单状态
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	/**
	 * 创建退款订单
	 * @param bizOrderNum
	 * @return
	 */
	@RequestMapping(value = "/refundOrder")
	public String createV2RefundOrder(String bizOrderNum,String refundOrderId) {
		Map<String,String> params=new HashMap<String,String>();
		params.put("amount", "1");
		params.put("refundOrderId", refundOrderId);
		params.put("appKey", payConfig.getAppKey());
		String signature = SignatureHelper.computeSignature(params, payConfig.getPaySecretKey());
		params.put("namespaceId", payConfig.getNamespaceId());
		params.put("bizOrderNum", bizOrderNum);
		params.put("callbackUrl", payConfig.getCallBackUri());
		params.put("signature", signature);
		String res=null;
		try {
			HttpResponse doPost = HttpUtils.doPost(payConfig.getBaseUri(), payConfig.getRefundOrderPath(), "", new HashMap<String, String>(), null, params);
			res=EntityUtils.toString(doPost.getEntity(),"UTF-8");
			log.info("国贸服务:退款->{}",res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 查询订单
	 * @param bizOrderNum
	 * @return
	 */
	@RequestMapping(value = "/queryOrder")
	public String queryV2RefundOrder(String bizOrderNum,String orderId) {
		log.info("payConfig.appkey={}",payConfig.getAppKey());
		Map<String,String> params=new HashMap<String,String>();
		params.put("orderId", orderId);
		params.put("appKey", payConfig.getAppKey());
		String signature = SignatureHelper.computeSignature(params, payConfig.getPaySecretKey());
		params.put("signature", signature);
		String res=null;
		try {
			HttpResponse doPost = HttpUtils.doPost(payConfig.getBaseUri(), payConfig.getQueryOrderPath(), "", new HashMap<String, String>(), null, params);
			res=EntityUtils.toString(doPost.getEntity(),"UTF-8");
			log.info("国贸服务:查询订单->{}",res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}	

	private String urlEncode(String s) {
		try {
			String result = java.net.URLEncoder.encode(s, "UTF-8");
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
