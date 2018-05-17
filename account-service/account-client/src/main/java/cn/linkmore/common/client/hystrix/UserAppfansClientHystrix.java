package cn.linkmore.common.client.hystrix;


import org.springframework.stereotype.Component;

import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.common.client.UserAppfansClient;

@Component
public class UserAppfansClientHystrix implements UserAppfansClient{

	@Override
	public ResUserAppfans selectByUserId(Long userId) {
		return null;
	}

	
}

