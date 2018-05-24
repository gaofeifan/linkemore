package cn.linkmore.third.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.linkmore.third.config.DockingConfig;
import cn.linkmore.third.request.ReqOrder;
import cn.linkmore.third.service.DockingService;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

@Service
public class DockingServiceImpl implements DockingService {
	@Autowired
	private DockingConfig dockConfig;

	@Override
	@Async
	public void order(ReqOrder ro) {
		HttpUtil.sendJson(dockConfig.getOrderUrl(), JsonUtil.toJson(ro)); 
	} 
	
}
