package cn.linkmore.prefecture.client.hystrix;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsEntVipUserClient;

@Component
public class OpsEntVipUserClientHystrix  implements OpsEntVipUserClient{

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
