package cn.linkmore.ops.admin.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminUser;

/**
 * Service - 线下管理员
 * @author jiaohanbin
 * @version 2.0
 */
public interface AdminUserService {

	int delete(List<Long> ids);
	
	int save(ReqAdminUser admin);
	
	int update(ReqAdminUser admin);
	
	//ResAdminUser find(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	Boolean check(ReqCheck reqCheck);

	Tree findTree();

	void bind(Long id, String authids);

	Map<String, Object> resource(Long id);

	//List<ResAdminUser> findAll();
}
