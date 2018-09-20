package cn.linkmore.ops.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqStaffAppVersion;
import cn.linkmore.common.request.ReqWyAppVersion;
import cn.linkmore.ops.account.service.StaffAppVersionService;
import cn.linkmore.ops.ent.request.ReqAppVersion;
import cn.linkmore.util.ObjectUtils;
/**
 * @author   GFF
 * @Date     2018年9月19日
 * @Version  v2.0
 */
@Service
public class StaffAppVersionServiceImpl implements StaffAppVersionService {

	@Resource
	private BaseVersionClient baseVersionClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return baseVersionClient.findStaffPage(pageable);
	}

	@Override
	public void save(ReqStaffAppVersion auth) {
		this.baseVersionClient.saveStaff(auth);
	}

	@Override
	public void update(ReqStaffAppVersion auth) {
		this.baseVersionClient.updateStaff(auth);
		
	}

	@Override
	public void delete(List<Long> ids) {
		this.baseVersionClient.deleteStaff(ids);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck check = new ReqCheck();
		check.setId(id);
		check.setValue(value);
		check.setProperty(property);
		return this.baseVersionClient.checkStaff(check );
	}
	
	
}
