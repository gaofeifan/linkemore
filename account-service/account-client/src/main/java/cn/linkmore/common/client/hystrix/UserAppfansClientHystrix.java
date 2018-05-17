package cn.linkmore.common.client.hystrix;


import org.springframework.stereotype.Component;

import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.common.client.UserAppfansClient;

@Component
public class UserAppfansClientHystrix implements UserAppfansClient{

	@Override
	public ResUserAppfans selectByUserId(Long userId) {
		return null;
	}

	@Override
	public ResUserLogin wxLogin(ReqUserAppfans appfans) {
		return null;
	}

	
}

