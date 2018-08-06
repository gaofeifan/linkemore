package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqVipUser;
import cn.linkmore.prefecture.client.OpsEntVipUserClient;

@Component
public class OpsEntVipUserClientHystrix  implements OpsEntVipUserClient{

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ReqVipUser user) {
		System.out.println(user.getMobile());
	}

	@Override
	public void update(ReqVipUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

}
