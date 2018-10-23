package cn.linkmore.third.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.third.config.AppLongPayConfig;
import cn.linkmore.third.pay.JianHanglong.JianHangLong;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.service.AppLongPayService;

/**
 * 龙支付接口实现
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
@Service
public class AppLongPayServiceImpl implements AppLongPayService {

	@Resource
	private AppLongPayConfig config;
	@Override
	public String order(ReqLongPay longPay) {
		JianHangLong.create(longPay, config);
		return null;
	}
	@Override
	public boolean callbackMsg(Map<String, Object> map) {
		JianHangLong.callbackMsg(map);
		return false;
	}

	
}
