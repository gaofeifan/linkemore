package cn.linkmore.ops.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.WhitelistClient;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.WhitelistService;
import cn.linkmore.ops.request.ReqWhitelist;
import cn.linkmore.util.ObjectUtils;
/**
 * 权限模块 - 类记录--接口实现
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Service
public class WhitelistServiceImpl implements WhitelistService {

	@Resource
	private WhitelistClient whitelistClient;
	
	@Override
	public void save(ReqWhitelist record) {
		cn.linkmore.account.request.ReqWhitelist object = ObjectUtils.copyObject(record, new cn.linkmore.account.request.ReqWhitelist());
		this.whitelistClient.save(object);
	}

	@Override
	public void update(ReqWhitelist record) {
		cn.linkmore.account.request.ReqWhitelist object = ObjectUtils.copyObject(record, new cn.linkmore.account.request.ReqWhitelist());
		this.whitelistClient.update(object);
	}

	@Override
	public void delete(List<Long> ids) {
		this.whitelistClient.delete(ids);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck check = new ReqCheck();
		check.setProperty(property);
		check.setValue(value);
		check.setId(id);
		Boolean flag = this.whitelistClient.check(check );
		return flag;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.whitelistClient.list(pageable);
		return page;
	}

	
}
