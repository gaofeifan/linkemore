package cn.linkmore.ops.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.RollbackClient;
import cn.linkmore.ops.coupon.service.RollbackService;

@Service
public class RollbackServiceImpl implements RollbackService {
	
	@Autowired
	private RollbackClient rollbackClient;
	
	public ViewPage findPage(ViewPageable pageable) { 
		return this.rollbackClient.list(pageable);
	}
}
