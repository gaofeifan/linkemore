package cn.linkmore.ops.ent.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.request.ReqBindStaffAuth;

/**
 * 员工接口
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
public interface StaffService {

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  查询权限树
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Tree tree(HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void bind(ReqBindStaffAuth staffAuth);

	/**
	 * @Description  查询资源
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> resource(Long resource);

}
