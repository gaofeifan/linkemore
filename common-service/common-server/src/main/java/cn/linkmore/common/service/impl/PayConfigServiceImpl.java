package cn.linkmore.common.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.PayConfigClusterMapper;
import cn.linkmore.common.request.ReqFinshOrder;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResFinshOrder;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.common.service.PayConfigService;

@Service
public class PayConfigServiceImpl implements PayConfigService{
	
	
	@Autowired
	PayConfigClusterMapper  payConfigClusterMapper;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResPayConfig getConfig(ReqPayConfig reqPayConfig) {
		return payConfigClusterMapper.getConfig(reqPayConfig);
	}

	@Override
	public List<ResFinshOrder> getOrder(ReqFinshOrder reqFinshOrder) {
		return 	payConfigClusterMapper.getOrder(reqFinshOrder);
	}
	
	
	
	
	
}
