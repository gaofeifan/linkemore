package cn.linkmore.ops.ent.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.request.ReqAddEntStaff;
import cn.linkmore.ops.ent.request.ReqBindStaffAuth;
import cn.linkmore.ops.ent.service.StaffService;
import cn.linkmore.prefecture.client.OpsStaffAuthClient;
/**
 * 员工接口实现
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
@Service
public class StaffServiceImpl implements StaffService {

	@Resource
	private OpsStaffAuthClient staffClient;
	
	

	@Override
	public void bind(ReqBindStaffAuth staffAuth) {
		ReqStaffAuthBind staff = new ReqStaffAuthBind();
		List<Long> ids = new ArrayList<>();
		String[] strings = staffAuth.getAuthIds().split(",");
		for (String string : strings) {
			ids.add(Long.decode(string));
		}
		staff.setAuthIds(ids);
		staff.setStaffId(staffAuth.getStaffId());
		this.staffClient.bind(staff );
	}

	@Override
	public Map<String, Object> resource(Long resource) {
		return this.staffClient.resouce(resource);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.staffClient.findPage(pageable);
	}

	@Override
	public Tree tree(HttpServletRequest request) {
		return this.staffClient.tree();
	}

	@Override
	public void delete(Long id) {
		this.staffClient.delete(id);
	}

	@Override
	public void update(ReqAddEntStaff staff) {
		this.staffClient.update(staff);
	}

	@Override
	public void save(ReqAddEntStaff staff) {
		this.staffClient.save(staff);
	}

	

	
	
}
