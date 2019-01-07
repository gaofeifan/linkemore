package cn.linkmore.third.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.third.config.AppLoongPayConfig;
import cn.linkmore.third.pay.loong.JianHangLong;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.request.ReqLoongPayVerifySign;
import cn.linkmore.third.response.ResLoongPay;
import cn.linkmore.third.service.AppLoongPayService;

/**
 * 龙支付接口实现
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
@Service
public class AppLoongPayServiceImpl implements AppLoongPayService {

	@Resource
	private AppLoongPayConfig config;
	@Override
	public ResLoongPay order(ReqLongPay longPay) {
		return JianHangLong.create(longPay, config);
	}
	@Override
	public boolean callbackMsg(Map<String, Object> map) {
		JianHangLong.callbackMsg(map);
		return false;
	}
	@Override
	public boolean verifySigature(ReqLoongPayVerifySign verifySign) {
		verifySign.setPub(config.getPubKey());
		return JianHangLong.verifySigature(verifySign);
	}

	
	
}
