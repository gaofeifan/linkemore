package cn.linkmore.ops.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqPage;
import cn.linkmore.ops.service.PageService;
import cn.linkmore.security.client.PageClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PageServiceImpl implements PageService {

	@Autowired
	private PageClient pageClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.pageClient.list(pageable);
	}

	@Override
	public int save(ReqPage reqPage) {
		cn.linkmore.security.request.ReqPage reqPageSecurity = new cn.linkmore.security.request.ReqPage();
		reqPageSecurity = ObjectUtils.copyObject(reqPage, reqPageSecurity);
		return this.pageClient.save(reqPageSecurity);
	}

	@Override
	public int update(ReqPage reqPage) {
		cn.linkmore.security.request.ReqPage reqPageSecurity = new cn.linkmore.security.request.ReqPage();
		reqPageSecurity = ObjectUtils.copyObject(reqPage, reqPageSecurity);
		return this.pageClient.update(reqPageSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.pageClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.pageClient.check(reqCheckSecurity);
	}
	
	
}
