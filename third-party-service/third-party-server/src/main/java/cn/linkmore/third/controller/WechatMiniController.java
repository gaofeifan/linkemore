package cn.linkmore.third.controller;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.pay.wxpay.PaySign;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;
import cn.linkmore.third.service.WechatMiniService;
import cn.linkmore.util.JsonUtil;
/**
 * Controller - 小程序
 * @author liwenlong
 * @version 2.0
 */
@RestController
@RequestMapping("/feign/wechat-mini")
public class WechatMiniController {
	@Autowired
	private  WechatMiniService wechatMiniService;
	/**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/session/{code}", method = RequestMethod.GET) 
	@ResponseBody
	public ResMiniSession getSession(@PathVariable("code") String code) {
		return this.wechatMiniService.getSession(code);
	}
	
	/**
	 * 下单
	 * @param wechat
	 * @return
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public ResWechatMiniOrder order(@RequestBody ReqWechatMiniOrder wechat) {
		ResWechatMiniOrder order = null;
		try {
			order = this.wechatMiniService.order(wechat); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return order;
	}
	/**
	 * 核验订单
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json) {
		Map<String,String> map = JsonUtil.toObject(json, HashMap.class);
		return PaySign.isValidSign(PaySign.strmapToTreeMap(map));
	} 
}
