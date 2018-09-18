package cn.linkmore.ops.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionClient;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.ops.base.service.BaseVersionService;
import cn.linkmore.ops.request.ReqBaseVersion;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.util.ObjectUtils;

/**
 * 版本管理接口实现
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@Service
public class BaseVersionServiceImpl implements BaseVersionService {

	@Resource
	private BaseVersionClient baseVersionClient;
	
	
	@Override
	public void save(ReqBaseVersion record) {
		ReqAppVersion version = ObjectUtils.copyObject(record, new ReqAppVersion());
		this.baseVersionClient.saveApp(version);
	}

	@Override
	public void update(ReqBaseVersion record) {
		ReqAppVersion version = ObjectUtils.copyObject(record, new ReqAppVersion());
		this.baseVersionClient.updateApp(version);
	}

	@Override
	public void delete(List<Long> ids) {
		this.baseVersionClient.deleteAppById(ids);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		cn.linkmore.common.request.ReqCheck object = new cn.linkmore.common.request.ReqCheck();
		object.setId(id);
		object.setValue(value);
		object.setProperty(property);
		return this.baseVersionClient.check(object);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.baseVersionClient.findPage(pageable);
		return page;
	}

	@Override
	public ViewPage findUserPage(ViewPageable pageable) {
		ViewPage page = this.baseVersionClient.findUserPage(pageable);
		return page;
	}

}
