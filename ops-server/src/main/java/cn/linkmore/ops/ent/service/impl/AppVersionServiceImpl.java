package cn.linkmore.ops.ent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.WyAppVersionClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqWyAppVersion;
import cn.linkmore.ops.ent.request.ReqAppVersion;
import cn.linkmore.ops.ent.service.AppVersionService;
import cn.linkmore.util.ObjectUtils;

/**
 * @author   GFF
 * @Date     2018年9月19日
 * @Version  v2.0
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private WyAppVersionClient wyAppVersionClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return wyAppVersionClient.findPage(pageable);
	}

	@Override
	public void save(ReqAppVersion auth) {
		this.wyAppVersionClient.save(ObjectUtils.copyObject(auth, new ReqWyAppVersion()));
	}

	@Override
	public void update(ReqAppVersion auth) {
		this.wyAppVersionClient.update(ObjectUtils.copyObject(auth, new ReqWyAppVersion()));
		
	}

	@Override
	public void delete(List<Long> ids) {
		this.wyAppVersionClient.delete(ids);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck check = new ReqCheck();
		check.setId(id);
		check.setValue(value);
		check.setProperty(property);
		return this.wyAppVersionClient.check(check );
	}

	
	
}
