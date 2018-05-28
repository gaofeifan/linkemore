package cn.linkmore.ops.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqInterface;
import cn.linkmore.ops.response.ResInterface;
import cn.linkmore.ops.service.InterfaceService;
import cn.linkmore.security.client.InterfaceClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

	@Autowired
	private InterfaceClient interfaceClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.interfaceClient.list(pageable);
	}

	@Override
	public int save(ReqInterface reqInterface) {
		cn.linkmore.security.request.ReqInterface reqInterfaceSecurity = new cn.linkmore.security.request.ReqInterface();
		reqInterfaceSecurity = ObjectUtils.copyObject(reqInterface, reqInterfaceSecurity);
		return this.interfaceClient.save(reqInterfaceSecurity);
	}

	@Override
	public int update(ReqInterface reqInterface) {
		cn.linkmore.security.request.ReqInterface reqInterfaceSecurity = new cn.linkmore.security.request.ReqInterface();
		reqInterfaceSecurity = ObjectUtils.copyObject(reqInterface, reqInterfaceSecurity);
		return this.interfaceClient.update(reqInterfaceSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.interfaceClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.interfaceClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.interfaceClient.tree();
	}

	@Override
	public List<ResInterface> findAll() {
		List<ResInterface> resInterfaceList = new ArrayList<ResInterface>();
		ResInterface resInter = null;
		List<cn.linkmore.security.response.ResInterface> interList = this.interfaceClient.findAll();
		for(cn.linkmore.security.response.ResInterface inter :interList) {
			resInter = new ResInterface();
			resInter = ObjectUtils.copyObject(inter, resInter);
			resInterfaceList.add(resInter);
		}
		return resInterfaceList;
	}
	
}
