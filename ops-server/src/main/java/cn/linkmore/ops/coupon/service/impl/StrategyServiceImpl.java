package cn.linkmore.ops.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.StrategyClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqStrategy;
import cn.linkmore.ops.coupon.service.StrategyService;

@Service
public class StrategyServiceImpl implements StrategyService {
	
	@Autowired
	private StrategyClient strategyClient; 
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		return this.strategyClient.list(pageable);
	}

	@Override
	public int save(ReqStrategy record) {
		return this.strategyClient.save(record);
	}
	
	@Override
	public int update(ReqStrategy record) {
		return this.strategyClient.update(record);
	}

	@Override
	public int delete(Long id) {
		return this.strategyClient.delete(id); 
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.strategyClient.check(reqCheck); 
	}
}
