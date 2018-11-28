package cn.linkmore.third.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.service.H5PayService;
import cn.linkmore.third.trade.wx.WeChatPay;

@Service
public class H5PayServiceImpl implements H5PayService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResH5Degree wxOpenid(ReqH5Token reqH5Token) {
		String appId = reqH5Token.getAppid();
		String code = reqH5Token.getCode();
		String secret = reqH5Token.getAppsecret();
		ResH5Degree res = new ResH5Degree();
		String openid = WeChatPay.oauth2GetOpenid(appId, code, secret);
		res.setOpenid(openid);
		log.info("wxOpenid>>>>>>"+openid);
		return res;
	}

	@Override
	public ResH5Degree aliOpenid(ReqH5Token reqH5Token) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
