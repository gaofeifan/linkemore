package cn.linkmore.ops.security.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqPerson;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.ops.security.response.ResPersonRole;
import cn.linkmore.ops.security.response.ResRole;
import cn.linkmore.ops.shiro.Principal;

/**
 * Service接口 -权限模块- 后台用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface PersonService {
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	ResPerson findByUsername(String username);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * @param person
	 */
	int save(ReqPerson reqPerson);

	/**
	 * 更新
	 * @param person
	 * @return
	 */
	int update(ReqPerson reqPerson);
	
	
	/**
	 * 更新
	 * @param person
	 * @return
	 */
	int loginUpdate(ReqPerson person);

	/**
	 * 记录检验
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);  

	/**
	 * 获取登录人的权限列表
	 * @param principal
	 * @return
	 */
	List<String> findAuthList(Principal principal);


	/**
	 * 解除锁定
	 * @param id
	 */ 
	void unlock(Long id);
	
	/**
	 * 用户角色信息
	 * @param id
	 * @return
	 */
	List<ResPersonRole> personRoleList(Long id);
	
	/**
	 * 启用的所有角色信息
	 * @return
	 */
	List<ResRole> roleList();
	
	/**
	 * 用户绑定角色
	 * @param id
	 * @param ids
	 */
	void bind(Long id,String ids);



	void updatePassword(ResPerson person, String oldPassword, String password);

}
