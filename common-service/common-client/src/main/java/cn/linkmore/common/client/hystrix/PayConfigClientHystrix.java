package cn.linkmore.common.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.common.client.PayConfigClient;
import cn.linkmore.common.request.ReqFinshOrder;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.request.ReqPayRecord;
import cn.linkmore.common.response.ResFinshOrder;
import cn.linkmore.common.response.ResPayConfig;

@Component
public class PayConfigClientHystrix implements PayConfigClient{

	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResPayConfig getConfig(ReqPayConfig reqPayConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResFinshOrder> getOrder(ReqFinshOrder reqFinshOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrder(ReqPayRecord reqPayRecord) {
		// TODO Auto-generated method stub
		
	}
	
}
