package cn.linkmore.common.service;

import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;

public interface PayConfigService {

	ResPayConfig getConfig( ReqPayConfig reqPayConfig);
	
}
