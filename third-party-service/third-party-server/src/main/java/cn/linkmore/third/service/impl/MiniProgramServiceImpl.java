package cn.linkmore.third.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.third.config.MiniProgramConfig;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.service.MiniProgramService;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

@Service
public class MiniProgramServiceImpl implements MiniProgramService {
	
	private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session"; 

	@Autowired
	private MiniProgramConfig miniProgramConfig;
	
	@Override
	public ResMiniSession getSession(String code) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", miniProgramConfig.getAppId());
		param.put("secret", miniProgramConfig.getAppSecret());
		param.put("js_code", code);
		String json = HttpUtil.sendGet(SESSION_URL, param);
		ResMiniSession rms = JsonUtil.toObject(json, ResMiniSession.class);
		return rms;
	}

}
