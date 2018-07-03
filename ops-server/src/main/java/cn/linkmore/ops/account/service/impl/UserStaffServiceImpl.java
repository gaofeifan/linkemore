package cn.linkmore.ops.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.UserStaffService;
/**
 * 接口实现
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Service
public class UserStaffServiceImpl implements UserStaffService {

	@Resource
	private UserStaffClient userStaffClient;
	
	@Override
	public void save(ReqUserStaff record) {
		this.userStaffClient.save(record);
	}

	@Override
	public void update(ReqUserStaff record) {
		this.userStaffClient.update(record);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck check = new ReqCheck();
		check.setId(id);
		check.setProperty(property);
		check.setValue(value);
		return this.userStaffClient.check(check);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.userStaffClient.list(pageable);
		return page;
	}

}
