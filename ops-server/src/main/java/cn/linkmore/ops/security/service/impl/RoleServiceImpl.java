package cn.linkmore.ops.security.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqRole;
import cn.linkmore.ops.security.service.RoleService;
import cn.linkmore.security.client.RoleClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleClient roleClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.roleClient.list(pageable);
	}

	@Override
	public int save(ReqRole reqRole) {
		cn.linkmore.security.request.ReqRole reqRoleSecurity = new cn.linkmore.security.request.ReqRole();
		reqRoleSecurity = ObjectUtils.copyObject(reqRole, reqRoleSecurity);
		return this.roleClient.save(reqRoleSecurity);
	}

	@Override
	public int update(ReqRole reqRole) {
		cn.linkmore.security.request.ReqRole reqRoleSecurity = new cn.linkmore.security.request.ReqRole();
		reqRoleSecurity = ObjectUtils.copyObject(reqRole, reqRoleSecurity);
		return this.roleClient.update(reqRoleSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.roleClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.roleClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.roleClient.tree();
	}

	@Override
	public Map<String, Object> resource(Long id) {
		return this.roleClient.resource(id);
	}

	@Override
	public void bind(Long id, String pids, String eids) {
		this.roleClient.bind(id, pids, eids);
	}
	
	
}
