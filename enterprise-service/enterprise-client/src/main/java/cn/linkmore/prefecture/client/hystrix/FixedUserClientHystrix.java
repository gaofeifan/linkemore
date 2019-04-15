package cn.linkmore.prefecture.client.hystrix;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.prefecture.client.FixedUserClient;

@Component
public class FixedUserClientHystrix implements FixedUserClient{

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pick(ReqFixedUserPick reqFixedUserPick) {
		// TODO Auto-generated method stub
		
	}



}
