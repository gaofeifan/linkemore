package cn.linkmore.ops.security.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqMenu;
import cn.linkmore.security.response.ResMenu;

/**
 * Service接口 - 权限模块  - 菜单信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface MenuService {
	
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 保存信息
	 * @param menu
	 */
	int save(ReqMenu menu);
	
	/**
	 * 更新信息
	 * @param menu
	 * @return
	 */
	int update(ReqMenu menu);
	
	
	/**
	 * 删除信息
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 异步校验
	 * @param reqCheck
	 * @return
	 */
    Boolean check(ReqCheck reqCheck);

	/**
	 * 查询树
	 * @return
	 */ 
	Tree findTree();

	Map<String, Object> map();
	
	/**
	 * 获取用户授权菜单 
	 * @return
	 */
	public void cachePersonAuthList();
	
	/**
	 * 获取用户授权菜单 
	 * @return
	 */
	public List<ResMenu> findPersonAuthList(Long id);

}
