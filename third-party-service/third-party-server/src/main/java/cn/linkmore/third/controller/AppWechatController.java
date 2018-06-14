package cn.linkmore.third.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.pay.wxpay.PaySign;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.third.service.AppWechatService;
import cn.linkmore.util.JsonUtil;
/**
 * Controller - 微信支付[APP]
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/app-wechat")
public class AppWechatController {
	@Autowired
	private AppWechatService appWechatService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/fans/{code}", method = RequestMethod.GET) 
	@ResponseBody
	public ResFans getFans(@PathVariable("code") String code) {
		return this.appWechatService.getWechatFans(code);
	}
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public ResAppWechatOrder order(@RequestBody ReqAppWechatOrder wechat) {
		ResAppWechatOrder order = null;
		try {
			order = this.appWechatService.order(wechat);
			log.info("wechat order:{}",JsonUtil.toJson(order));
		} catch (JDOMException | IOException e) { 
			e.printStackTrace();
		}
		return order;
	}
	
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json) {
		Map<String,String> map = JsonUtil.toObject(json, HashMap.class);
		return PaySign.isValidSign(PaySign.strmapToTreeMap(map));
	}
}
