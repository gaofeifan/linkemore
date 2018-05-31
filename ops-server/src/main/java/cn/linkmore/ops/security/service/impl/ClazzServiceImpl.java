package cn.linkmore.ops.security.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqClazz;
import cn.linkmore.ops.security.service.ClazzService;
import cn.linkmore.security.client.ClazzClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class ClazzServiceImpl implements ClazzService {

	@Autowired
	private ClazzClient clazzClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.clazzClient.list(pageable);
	}

	@Override
	public int save(ReqClazz reqClazz) {
		cn.linkmore.security.request.ReqClazz reqClazzSecurity = new cn.linkmore.security.request.ReqClazz();
		reqClazzSecurity = ObjectUtils.copyObject(reqClazz, reqClazzSecurity);
		return this.clazzClient.save(reqClazzSecurity);
	}

	@Override
	public int update(ReqClazz reqClazz) {
		cn.linkmore.security.request.ReqClazz reqClazzSecurity = new cn.linkmore.security.request.ReqClazz();
		reqClazzSecurity = ObjectUtils.copyObject(reqClazz, reqClazzSecurity);
		return this.clazzClient.update(reqClazzSecurity);
		
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
