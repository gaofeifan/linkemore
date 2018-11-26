package cn.linkmore.order.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Transaction;
import cn.linkmore.order.config.OauthConfig;
import cn.linkmore.order.service.RedirectService;

@Service
public class RedirectServiceImpl implements RedirectService {

	@Autowired
	private OauthConfig oauthConfig;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 识别客户端并获取用户信息
	 */
	@Override
	public String distributed(HttpServletRequest request) {
		StringBuffer buferRedirect = new StringBuffer("redirect:");

		String paytype = null;
		String redirect = null;
		String openid = null;
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if (ua.indexOf(Transaction.wxclient) > 0) { // 是微信浏览器
			paytype = Transaction.WX;
			log.info("paytype->" + paytype);
		} else if (ua.indexOf(Transaction.aliclient) > 0) { // 支付宝浏览器
			paytype = Transaction.ZFB;
			log.info("paytype->" + paytype);
		} else {
			buferRedirect.append(oauthConfig.getH5Url());
			buferRedirect.append(Transaction.CLIENT);
			buferRedirect.append(Transaction.OTHER);
			buferRedirect.append(Transaction.OPENID);
			buferRedirect.append(openid);
			redirect = buferRedirect.toString();
			log.info("paytype->" + paytype + "redirect->" + redirect); // 其他浏览器
			return redirect;
		}
		// 跳转微信服务器
		String appid = "wxd167b1783e872df6";
		String redirect_uri = "http://test.linkmoreparking.cn/api/order/h5/a";
		redirect = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
				+ redirect_uri + "&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

		return redirect;
	}

	@Override
	public String auth(Map<String, String> params) {
		String code = params.get("code");
		log.info(code);
		// 跳转网页
		String AppSecret = "637c41ce97f055b9afd52a4344322285";
		String url = "redirect:http://pay.linkmoreparking.cn/?openid=000&code=" + code;
		return url;
	}

}
