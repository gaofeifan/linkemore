package cn.linkmore.user.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.TradePayType;
import cn.linkmore.order.client.PayClient;
import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.response.ResOrderWeixin;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqPayConfirm;
import cn.linkmore.user.response.ResPayCheckout;
import cn.linkmore.user.response.ResPayConfirm;
import cn.linkmore.user.response.ResPayWeixin;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.PayService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.XMLUtil;
@Service
public class PayServiceImpl implements PayService {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisService redisService; 
	
	@Autowired
	private PayClient payClient;

	@Override
	public ResPayCheckout checkout(Long orderId, HttpServletRequest request) { 
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ResOrderCheckout checkout = this.payClient.checkout(orderId, ru.getId());
		log.info("checkout({},{}):{}",orderId,ru.getId(),JsonUtil.toJson(checkout));
		ResPayCheckout result = null; 
		if(checkout!=null) {
			result = new ResPayCheckout();
			result.setAccountAmount(checkout.getAccountAmount());
			result.setTotalAmount(checkout.getTotalAmount());
			result.setPlateNumber(checkout.getPlateNumber());
			result.setParkingTime(checkout.getParkingTime());
			result.setPayType(checkout.getPayType());
			result.setPrefectureName(checkout.getPrefectureName());
			result.setStallName(checkout.getStallName());
			result.setCouponCount(checkout.getCouponCount());
			result.setEndTime(checkout.getEndTime());
			result.setStartTime(checkout.getStartTime());
			result.setOrderId(checkout.getOrderId()); 
		} 
		return result;
	}

	@Override
	public ResPayConfirm confirm(ReqPayConfirm rpc, HttpServletRequest request) {
		ReqOrderConfirm roc = new ReqOrderConfirm();
		roc.setAddress(request.getLocalAddr());
		roc.setCouponId(rpc.getCouponId());
		roc.setOrderId(rpc.getOrderId());
		roc.setPayType(rpc.getPayType());
		ResOrderConfirm confirm = this.payClient.confirm(roc);
		log.info("confirm:{}",JsonUtil.toJson(confirm));
		ResPayConfirm res = null;
		if(confirm!=null) {
			res = new ResPayConfirm(); 
			res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			res.setPayType(confirm.getPayType());
			
			if(confirm.getPayType().shortValue()==TradePayType.ALIPAY.type) {
				res.setAlipay(confirm.getAlipay());
				res.setNumber(confirm.getNumber());
			}else if(confirm.getPayType().shortValue()==TradePayType.WECHAT.type){
				res.setNumber(confirm.getNumber());
				ResOrderWeixin row = confirm.getWeixin();
				ResPayWeixin weixin = new ResPayWeixin();
				weixin.setAppid(row.getAppid());
				weixin.setNoncestr(row.getNoncestr());
				weixin.setPartnerid(row.getPartnerid());
				weixin.setPrepayid(row.getPrepayid());
				weixin.setSign(row.getSign());
				weixin.setTimestamp(row.getTimestamp());
				res.setWeixin(weixin);
			}else if(confirm.getPayType().shortValue()==TradePayType.APPLE.type){
				res.setApple(confirm.getApple());
				res.setNumber(confirm.getNumber());
			}
		}
		return res;
	}

	@Override
	public void verify(Long orderId, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		this.payClient.verify(orderId, ru.getId());
	}

	@Override
	public void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request)  {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(request);
			String json = JsonUtil.toJson(map);
			log.info("wechatOrderNotice:{}",json);
			Boolean flag = this.payClient.callback(json, TradePayType.WECHAT.type);
			if(flag) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("return_msg", "OK");
				param.put("return_code", "SUCCESS");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<xml>");
				for (Map.Entry<String, String> entry : param.entrySet()) {
					buffer.append("<" + entry.getKey() + ">");
					buffer.append("<![CDATA[" + entry.getValue() + "]]>");
					buffer.append("</" + entry.getKey() + ">");
				}
				buffer.append("</xml>");
				String result = new String(buffer.toString().getBytes(), "utf-8");
				// 微信通知返回业务结果为success 给微信返回成功信息
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(result);
				pw.flush();
				pw.close();
			} 
		} catch (Exception e) { 
			e.printStackTrace();
		}  
	}
	private final static String RESULT_SUCCESS="success";
	private final static String RESULT_FAILURE="fail";
	@Override
	public void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request) { 
		try {
			Map<String, String> paramMap = new HashMap<>();
			Map<String, String[]> map = request.getParameterMap(); 
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String param = it.next();
				String[] vals = map.get(param);
				String value = null != vals && vals.length > 0 ? (vals.length == 1 ? vals[0] : null) : null;
				if (null == value) {
					value = "";
					for (String v : vals) {
						value += "," + v;
					}
					value = value.substring(1, value.length());
				}
				paramMap.put(param, value);
			}  
			String json = JsonUtil.toJson(paramMap);
			log.info("alipayOrderNotice:{}",json);
			Boolean flag = false;
			try {
				flag = this.payClient.callback(json,  TradePayType.ALIPAY.type);
			}catch(Exception e) {
				e.printStackTrace();
			}
			PrintWriter pw = response.getWriter(); 
			pw.print(flag?RESULT_SUCCESS:RESULT_FAILURE);
			pw.flush();
			pw.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void appleOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		Enumeration<String> em = request.getParameterNames();
    	String name = null;
    	Map<String,String> respData = new HashMap<String,String>(); 
    	while(em.hasMoreElements()){ 
    		name = em.nextElement();
    		respData.put(name, request.getParameter(name)); 
    	} 
    	String json = JsonUtil.toJson(respData);
    	this.payClient.callback(json, TradePayType.APPLE.type); 
	}
	
}
