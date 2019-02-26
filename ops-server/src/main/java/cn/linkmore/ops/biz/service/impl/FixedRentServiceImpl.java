package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.ops.biz.service.FixedRentService;
import cn.linkmore.prefecture.client.OpsFixedRentClient;

@Service
public class FixedRentServiceImpl implements FixedRentService {
	@Autowired
	private OpsFixedRentClient opsFixedRentClient;
	
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsFixedRentClient.list(pageable);
	}
	
	@Override
	public List<ResStall> stallList(Map<String, Object> map) {
		return opsFixedRentClient.stallList(map);
	}
	
	@Override
	public List<ResStall> freeStallList(Map<String, Object> map) {
		return opsFixedRentClient.freeStallList(map);
	}
	
	@Override
	public int insert(ReqFixedRent reqFixedRent) {
		return opsFixedRentClient.insert(reqFixedRent);
	}

	@Override
	public int update(ReqFixedRent reqFixedRent) {
		// TODO Auto-generated method stub
		return opsFixedRentClient.update(reqFixedRent);
	}

	@Override
	public int deleteStall(Map<String, Object> map) {
		return  opsFixedRentClient.deleteStall(map);
	}

	@Override
	public int open(Map<String, Object> map) {
		return  opsFixedRentClient.open(map);
	}

	@Override
	public int close(Map<String, Object> map) {
		return  opsFixedRentClient.close(map);
	}

	@Override
	public ResFixedRent view(Long fixedId) {
		return  opsFixedRentClient.view(fixedId);
	}

	@Override
	public String check(ReqFixedRent reqFixedRent) {
		return  opsFixedRentClient.check(reqFixedRent);
	}

}
