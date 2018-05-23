package cn.linkmore.security.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Menu;
import cn.linkmore.security.request.ReqCheck;

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
	void save(Menu menu);
	
	/**
	 * 更新信息
	 * @param menu
	 * @return
	 */
	Menu update(Menu menu);
	
	
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
	Integer check(ReqCheck reqCheck);

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

}
