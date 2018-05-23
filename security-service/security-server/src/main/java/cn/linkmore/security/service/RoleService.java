package cn.linkmore.security.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;

/**
 * Service接口 - 权限模块 - 角色信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface RoleService {

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * @param role
	 */
	void save(Role role);

	/**
	 * 更新
	 * @param role
	 * @return
	 */
	Role update(Role role);

	/**
	 * 记录检验
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 资源树
	 * @return
	 */
	Tree findTree();

	/**
	 * 角色资源
	 * @return
	 */
	Map<String, Object> resource(Long id);

	/**
	 * 绑定
	 * @param id
	 * @param pids
	 * @param eids
	 */
	void bind(Long id, String pids, String eids); 

}
