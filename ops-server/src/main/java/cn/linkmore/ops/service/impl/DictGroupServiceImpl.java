package cn.linkmore.ops.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqDictGroup;
import cn.linkmore.ops.service.DictGroupService;
import cn.linkmore.security.client.DictGroupClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class DictGroupServiceImpl implements DictGroupService {

	@Autowired
	private DictGroupClient clazzClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.clazzClient.list(pageable);
	}

	@Override
	public int save(ReqDictGroup reqDictGroup) {
		cn.linkmore.security.request.ReqDictGroup reqDictGroupSecurity = new cn.linkmore.security.request.ReqDictGroup();
		reqDictGroupSecurity = ObjectUtils.copyObject(reqDictGroup, reqDictGroupSecurity);
		return this.clazzClient.save(reqDictGroupSecurity);
	}

	@Override
	public int update(ReqDictGroup reqDictGroup) {
		cn.linkmore.security.request.ReqDictGroup reqDictGroupSecurity = new cn.linkmore.security.request.ReqDictGroup();
		reqDictGroupSecurity = ObjectUtils.copyObject(reqDictGroup, reqDictGroupSecurity);
		return this.clazzClient.update(reqDictGroupSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.clazzClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.clazzClient.check(reqCheckSecurity);
	}
	
	
}
