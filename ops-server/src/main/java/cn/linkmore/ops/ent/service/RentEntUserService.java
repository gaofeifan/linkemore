package cn.linkmore.ops.ent.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;

public interface RentEntUserService {

	ViewPage findPage(ViewPageable pageable);

	void save(ReqRentEntUser ent);

	void update(ReqRentEntUser ent);

	void deleteIds(List<Long> ids);

	ViewPage findPageI(ViewPageable pageable);

	void updateI(ReqRentUser ent);

	void deleteIdsI(List<Long> ids);

	void saveI(ReqRentUser ent);

	boolean exists(ReqRentEntUser reqRentEntUser);



}
