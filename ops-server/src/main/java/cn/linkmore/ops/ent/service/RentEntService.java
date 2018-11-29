package cn.linkmore.ops.ent.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.prefecture.response.ResStall;

public interface RentEntService {

	ViewPage findPage(ViewPageable pageable);

	void save(cn.linkmore.ops.ent.request.ReqRentEnt ent);

	void update(ReqRentEnt ent);

	void delete(List<Long> ids);

	List<ResStall> stallList(HttpServletRequest request);

	ViewPage stallListCompany(ViewPageable pageable);

	List<Tree> tree();

}
