package cn.linkmore.ops.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqMenu;
import cn.linkmore.ops.service.MenuService;
import cn.linkmore.security.client.MenuClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuClient menuClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.menuClient.list(pageable);
	}

	@Override
	public int save(ReqMenu reqMenu) {
		cn.linkmore.security.request.ReqMenu reqMenuSecurity = new cn.linkmore.security.request.ReqMenu();
		reqMenuSecurity = ObjectUtils.copyObject(reqMenu, reqMenuSecurity);
		return this.menuClient.save(reqMenuSecurity);
	}

	@Override
	public int update(ReqMenu reqMenu) {
		cn.linkmore.security.request.ReqMenu reqMenuSecurity = new cn.linkmore.security.request.ReqMenu();
		reqMenuSecurity = ObjectUtils.copyObject(reqMenu, reqMenuSecurity);
		return this.menuClient.update(reqMenuSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.menuClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.menuClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.menuClient.tree();
	}

	@Override
	public Map<String, Object> map() {
		return this.menuClient.map();
	}

	@Override
	public void cachePersonAuthList() {
		
	}
	
	
}
