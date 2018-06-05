package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminAuth;

/**
 * Service - 线下管理员 授权
 * @author jiaohanbin
 * @version 2.0
 */
public interface AdminAuthService {

	int delete(List<Long> ids);
	
	int save(ReqAdminAuth admin);
	
	int update(ReqAdminAuth admin);
	
	ResAdminAuth find(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	Integer check(ReqCheck reqCheck);

	Tree findTree();

	void bind(Long id, String json,String citys,String pres);

	Map<String, Object> resource(Long id);

}
