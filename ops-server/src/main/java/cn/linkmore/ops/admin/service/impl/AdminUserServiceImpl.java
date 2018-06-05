package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.AdminUserService;
import cn.linkmore.prefecture.client.AdminAuthClient;
import cn.linkmore.prefecture.client.AdminUserClient;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
/**
 * 
 * Service实现类 - 线下管理员
 * @author zhaoyufei
 *
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserClient adminUserClient;
	
	/*
	 * 管理员列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.adminUserClient.list(pageable);
	}
	
	/*
	 * 检查手机号重复
	 */
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.adminUserClient.check(reqCheck);
	}
	/*
	 * 保存
	 */
	@Override
	public int save(ReqAdminUser admin) {
		return this.adminUserClient.save(admin);
	}
	
	@Override
	public int update(ReqAdminUser admin) {
		return this.adminUserClient.update(admin);
	}
	/*
	 * 资源树
	 */
	@Override
	public Tree findTree() {
		return this.adminUserClient.tree();
	}

	@Override
	public int delete(List<Long> ids) {
		return this.adminUserClient.delete(ids);
	}

	/*
	 * 绑定
	 */
	@Override
	public void bind(Long id, String authids) {
		this.adminUserClient.bind(id, authids);
	}
	/*
	 * 权限回显
	 */
	@Override
	public Map<String, Object> resource(Long id) {
		return this.adminUserClient.resource(id);
	}

	/*@Override
	public List<ResAdminUser> findAll() {
		return this.adminUserClient.findAll();
	}*/

}
