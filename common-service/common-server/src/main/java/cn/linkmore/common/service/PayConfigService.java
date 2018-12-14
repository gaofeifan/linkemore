package cn.linkmore.common.service;

import java.util.List;
import cn.linkmore.common.request.ReqFinshOrder;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResFinshOrder;
import cn.linkmore.common.response.ResPayConfig;

public interface PayConfigService {

	ResPayConfig getConfig( ReqPayConfig reqPayConfig);
	
	List<ResFinshOrder> getOrder( ReqFinshOrder reqFinshOrder);
	
}
