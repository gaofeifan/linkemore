package cn.linkmore.third.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

	class OrderThread extends Thread{
		private ReqOrder ro;
		public OrderThread(ReqOrder ro) {
			this.ro = ro;
		}
		public void run() {
			HttpUtil.sendJson(dockConfig.getOrderUrl(), JsonUtil.toJson(ro)); 
		} 
	}
	
	@Override 
	public void order(ReqOrder ro) {
		Thread thread = new OrderThread(ro);
		thread.start();
	} 
	
}
