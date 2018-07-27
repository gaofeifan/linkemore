package cn.linkmore.ops.ent.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.ops.ent.request.ReqBindStaffAuth;
import cn.linkmore.ops.ent.service.OperateService;
import cn.linkmore.prefecture.client.OpsOperateAuthClient;
@Service
public class OperateServiceImpl implements OperateService {
	
	@Resource
	private OpsOperateAuthClient opsOperateAuthClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.opsOperateAuthClient.list(pageable);
		return page;
	}

	@Override
	public List<Tree> tree(HttpServletRequest request) {
		List<Tree> list = this.opsOperateAuthClient.tree();
		return list;
	}

	@Override
	public void bind(ReqOperateBind staffAuth) {
		this.opsOperateAuthClient.bind(staffAuth);
		
	}

	@Override
	public Map<String, Object> resource(Long id) {
		Map<String, Object> map = this.opsOperateAuthClient.resource(id);
		return map;
	}

	@Override
	public void save(ReqOperateAuth auth) {
		this.opsOperateAuthClient.save(auth);
	}

	@Override
	public void update(ReqOperateAuth auth) {
		this.opsOperateAuthClient.update(auth);
	}

	@Override
	public void delete(List<Long> ids) {
//		this.opsOperateAuthClient.delete(ids);
	}

}
