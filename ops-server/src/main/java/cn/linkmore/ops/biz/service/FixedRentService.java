package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;

public interface FixedRentService {
	ViewPage findPage(ViewPageable pageable);

	List<ResStall> freeStallList(Map<String, Object> map);

	List<ResStall> stallList(Map<String, Object> map);
	
	ResFixedRent view(Long fixedId);
	
	int insert(ReqFixedRent reqFixedRent);

	int update(ReqFixedRent reqFixedRent);
	
	int deleteStall(Map<String, Object> map);
	
	int open(Map<String, Object> map);

	int close(Map<String, Object> map);

	String check(ReqFixedRent reqFixedRent);
}
