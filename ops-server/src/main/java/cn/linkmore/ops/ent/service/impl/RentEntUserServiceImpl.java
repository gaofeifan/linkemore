package cn.linkmore.ops.ent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.ops.ent.service.RentEntUserService;
import cn.linkmore.prefecture.client.OpsRentEntUserClient;
@Service
public class RentEntUserServiceImpl implements RentEntUserService{

	@Resource
	private OpsRentEntUserClient opsRentEntUserClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.opsRentEntUserClient.list(pageable);
	}

	@Override
	public void save(ReqRentEntUser ent) {
		this.opsRentEntUserClient.save(ent);
	}

	@Override
	public void update(ReqRentEntUser ent) {
		this.opsRentEntUserClient.update(ent);
		
	}

	@Override
	public void deleteIds(List<Long> ids) {
		this.opsRentEntUserClient.delete(ids);
	}

	
}
