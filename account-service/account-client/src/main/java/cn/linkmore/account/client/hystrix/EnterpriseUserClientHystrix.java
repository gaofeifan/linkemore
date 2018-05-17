package cn.linkmore.account.client.hystrix;


import org.springframework.stereotype.Component;

import cn.linkmore.account.client.EnterpriseUserClient;
import cn.linkmore.account.response.ResEnterpriseUser;

@Component
public class EnterpriseUserClientHystrix implements EnterpriseUserClient{

	@Override
	public ResEnterpriseUser selectById(Long userId) {
		return null;
	}

}

