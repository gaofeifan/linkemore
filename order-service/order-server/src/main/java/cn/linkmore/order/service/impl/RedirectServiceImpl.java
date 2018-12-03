package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.Transaction;
import cn.linkmore.common.client.PayConfigClient;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.order.config.OauthConfig;
import cn.linkmore.order.controller.h5.request.ReqPayParm;
import cn.linkmore.order.controller.h5.response.ResPayParm;
import cn.linkmore.order.entity.AauthConfig;
import cn.linkmore.order.service.RedirectService;
import cn.linkmore.third.client.H5PayClient;
import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;

@Service
public class RedirectServiceImpl implements RedirectService {

	@Autowired
	private OauthConfig oauthConfig;
	@Autowired
	private PayConfigClient payConfigClient;
	@Autowired
	H5PayClient h5PayClient;
	@Autowired
	private AauthConfig AuthConfig;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 识别客户端并获取code
	 */
	@Override
	public String distributed(Map<String, String> params, HttpServletRequest request) {
		String paytype = null, redirect = null, redirect_uri = null;
		Long preId = Long.valueOf(params.get("preId"));
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf(Transaction.wxclient) > 0) { // 是微信浏览器
			paytype = Transaction.WX;
			redirect_uri = oauthConfig.getWxCodeUrl();
			log.info("paytype->" + paytype);
		} else if (ua.indexOf(Transaction.aliclient) > 0) { // 支付宝浏览器
			paytype = Transaction.ZFB;
			redirect_uri = oauthConfig.getZfbCodeUrl();
			log.info("paytype->" + paytype);
		} else {
			log.info("paytype->" + paytype + "redirect->" + redirect); // 其他浏览器
			redirect_uri = oauthConfig.getH5Url();
			redirect = AuthConfig.getNoCode(redirect_uri);
			return redirect;
		}
		// 获取配置信息
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(preId);
		req.setType(paytype);
		ResPayConfig config = payConfigClient.getConfig(req);
		String appId = config.getAppId();
		// 跳转微信服务器
		if (paytype == Transaction.WX) {
			redirect = AuthConfig.getWxCode(appId, redirect_uri, preId);
		}
		// 跳转阿里服务器
		if (paytype == Transaction.ZFB) {
			redirect = AuthConfig.getZfbCode(appId, redirect_uri,preId);
		}
		log.info("redirect"+redirect);
		return redirect;
	}

	/**
	 * code换取用户信息
	 */
	@Override
	public String auth(Map<String, String> params) {
		String code = params.get("code");
		String auth_code = params.get("auth_code");
		Long preId = Long.valueOf(params.get("state"));
		String paytype = params.get("type");
		String openId = null;
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(preId);
		req.setType(paytype);
		ResPayConfig config = payConfigClient.getConfig(req);
		// 获取身份id
		ReqH5Token open = new ReqH5Token();
		open.setAppid(config.getAppId());
		open.setAppsecret(config.getAppSecret());		
		// 跳转网页
		ResH5Degree res = new ResH5Degree();
		if (paytype == Transaction.WX) {
			open.setCode(code);
			res = h5PayClient.wxopenid(open);
		}
		if (paytype == Transaction.ZFB) {
			open.setCode(auth_code);
			res = h5PayClient.aliopenid(open);
		}
		if(res!=null) {
			openId = res.getOpenid();
		}
		return AuthConfig.h5Index(preId, openId, paytype);
	}

	@Override
	public ResH5Degree Openid(ReqH5Token reqH5Token) {
		ResH5Degree res =h5PayClient.wxopenid(reqH5Token);
		return res;
	}

	@Override
	public ResPayParm wxparm(ReqPayParm reqPayParm) {
	
		//查询当前需支付订单
		String orderId = String.valueOf(new Date().getTime());
		String detail = "凌猫停车";
		BigDecimal totalAmount = new BigDecimal(0.01);
		
		ReqPayConfig req = new ReqPayConfig();
		req.setPreId(reqPayParm.getPreId());
		req.setType(Transaction.WX);
		ResPayConfig config = payConfigClient.getConfig(req);
		log.info("config---"+JSON.toJSON(config));
		//获取支付凭证
		ReqH5Term reqH5Term = new ReqH5Term();
		reqH5Term.setNotifyUrl(oauthConfig.getNotifyUrl());
		reqH5Term.setAppId(config.getAppId());
		reqH5Term.setAppSecret(config.getAppSecret());
		reqH5Term.setDetail(detail);
		reqH5Term.setMchId(config.getMchId());
		reqH5Term.setMchKey(config.getMchKey());
		reqH5Term.setOpenId(reqPayParm.getOpenId());
		reqH5Term.setOrderId(orderId);
		reqH5Term.setTotalAmount(totalAmount);
		//获取支付凭证
		ResH5Term  term =	h5PayClient.wxpay(reqH5Term);
		if(term==null) {
			return null;
		}
		log.info("term---"+JSON.toJSON(term));
		ResPayParm parm = new ResPayParm();
		parm.setAppId(term.getAppId());
		parm.setNonceStr(term.getNonceStr());
		parm.setPack(term.getPack());
		parm.setPaySign(term.getPaySign());
		parm.setTimeStamp(term.getTimeStamp());
		return parm;
	}

}
