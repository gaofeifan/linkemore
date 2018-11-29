package cn.linkmore.ops.ent.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;

public interface RentEntUserService {

	ViewPage findPage(ViewPageable pageable);

	void save(ReqRentEntUser ent);

	void update(ReqRentEntUser ent);

	void deleteIds(List<Long> ids);

}
