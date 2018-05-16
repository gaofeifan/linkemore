package cn.linkmore.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.BaseDictClusterMapper;
import cn.linkmore.common.dao.master.BaseDictMasterMapper;
import cn.linkmore.common.service.BaseDictService;

@Service
public class BaseDictServiceImpl implements BaseDictService {

	@Resource
	private BaseDictClusterMapper baseDictClusterMapper;

	@Resource
	private BaseDictMasterMapper baseDictMasterMapper;
	
	
}
