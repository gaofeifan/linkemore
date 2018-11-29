package cn.linkmore.order.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.common.Transaction;
import cn.linkmore.common.client.PayConfigClient;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.order.config.OauthConfig;
import cn.linkmore.order.entity.AauthConfig;
import cn.linkmore.order.service.RedirectService;
import cn.linkmore.third.client.H5PayClient;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;

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
			redirect = AuthConfig.getZfbCode(appId, redirect_uri);
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
		open.setCode(code);
		// 跳转网页
		ResH5Degree res = new ResH5Degree();
		if (paytype == Transaction.WX) {
			res = h5PayClient.wxopenid(open);
		}
		if (paytype == Transaction.ZFB) {
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

}
