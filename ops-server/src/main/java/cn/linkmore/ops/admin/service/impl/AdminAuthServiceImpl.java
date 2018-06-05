package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.AdminAuthService;
import cn.linkmore.prefecture.client.AdminAuthClient;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
/**
 * 
 * Service实现类 - 线下管理员 授权
 * @author zhaoyufei
 *
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
	@Autowired
	private AdminAuthClient adminAuthClient;
	
	/*
	 * 管理员列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.adminAuthClient.list(pageable);
	}
	
	/*
	 * 检查
	 */
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.adminAuthClient.check(reqCheck);
	}
	/*
	 * 保存
	 */
	@Override
	public int save(ReqAdminAuth admin) {
		return this.adminAuthClient.save(admin);
	}
	
	@Override
	public int update(ReqAdminAuth admin) {
		return this.adminAuthClient.update(admin);
	}
	/*
	 * 资源树
	 */
	@Override
	public Tree findTree() {
		return this.adminAuthClient.tree();
	}
	
	@Override
	public int delete(List<Long> ids) {
		return this.adminAuthClient.delete(ids);
	}

	/*
	 * 绑定
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void bind(Long id, String json,String citys,String pres) {
		this.adminAuthClient.bind(id, citys, pres, json);
	}
	/*
	 * 权限回显
	 */
	@Override
	public Map<String, Object> resource(Long id) {
		return this.adminAuthClient.resource(id);
	}
}
