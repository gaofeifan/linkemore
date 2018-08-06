package cn.linkmore.ops.ent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqVipUser;
import cn.linkmore.ops.ent.service.VipUserService;
import cn.linkmore.prefecture.client.OpsEntVipUserClient;

@Service
public class VipUserServiceImpl implements VipUserService {

	@Resource
	private OpsEntVipUserClient opsEntVipUserClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {

		return opsEntVipUserClient.findPage(pageable);
		
	}

	@Override
	public void save(ReqVipUser auth) {
		 opsEntVipUserClient.save(auth);
	}

	@Override
	public void update(ReqVipUser auth) {
	
	}

	@Override
	public void delete(List<Long> ids) {
		
	}

}
