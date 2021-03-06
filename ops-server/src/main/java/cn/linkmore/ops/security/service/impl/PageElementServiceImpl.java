package cn.linkmore.ops.security.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqPageElement;
import cn.linkmore.ops.security.service.PageElementService;
import cn.linkmore.security.client.PageElementClient;
import cn.linkmore.security.response.ResAuthElement;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PageElementServiceImpl implements PageElementService {

	@Autowired
	private PageElementClient pageElementClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.pageElementClient.list(pageable);
	}

	@Override
	public int save(ReqPageElement reqPageElement) {
		cn.linkmore.security.request.ReqPageElement reqPageElementSecurity = new cn.linkmore.security.request.ReqPageElement();
		reqPageElementSecurity = ObjectUtils.copyObject(reqPageElement, reqPageElementSecurity);
		return this.pageElementClient.save(reqPageElementSecurity);
	}

	@Override
	public int update(ReqPageElement reqPageElement) {
		cn.linkmore.security.request.ReqPageElement reqPageElementSecurity = new cn.linkmore.security.request.ReqPageElement();
		reqPageElementSecurity = ObjectUtils.copyObject(reqPageElement, reqPageElementSecurity);
		return this.pageElementClient.update(reqPageElementSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.pageElementClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.pageElementClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.pageElementClient.tree();
	}

	@Override
	public Map<String, Object> map() {
		return this.pageElementClient.map();
	}

	/**
	 * 待处理
	 */
	@Override
	public List<ResAuthElement> findResAuthElementList() {
		return this.pageElementClient.findResAuthElementList();
	}
	
	
}
