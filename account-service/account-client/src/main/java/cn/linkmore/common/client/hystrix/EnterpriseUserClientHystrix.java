package cn.linkmore.common.client.hystrix;


import org.springframework.stereotype.Component;

import cn.linkmore.account.response.ResEnterpriseUser;
import cn.linkmore.common.client.EnterpriseUserClient;

@Component
public class EnterpriseUserClientHystrix implements EnterpriseUserClient{

	@Override
	public ResEnterpriseUser selectById(Long userId) {
		return null;
	}

}

