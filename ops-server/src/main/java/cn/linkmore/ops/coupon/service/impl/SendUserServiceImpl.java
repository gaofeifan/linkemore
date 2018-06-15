package cn.linkmore.ops.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.SendUserClient;
import cn.linkmore.ops.coupon.service.SendUserService;

@Service
public class SendUserServiceImpl implements SendUserService {
	
	@Autowired
	private SendUserClient sendUserClient;
	
	
	public ViewPage findPage(ViewPageable pageable) { 
		return this.sendUserClient.list(pageable);
	}	
}
