package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.ThemeClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTheme;
import cn.linkmore.coupon.response.ResEnterpriseBean;
import cn.linkmore.ops.coupon.service.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {
	@Autowired
	private ThemeClient themeClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		return this.themeClient.list(pageable);
	}

	@Override
	public int save(ReqTheme record) { 
		return this.themeClient.save(record);
	}
	
	@Override
	public int update(ReqTheme record) {
		return this.themeClient.update(record);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.themeClient.delete(ids); 
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.themeClient.check(reqCheck); 
	}

	@Override
	public List<ResEnterpriseBean> findEnterpriseList() {
		return this.themeClient.enterpriseList();
	}
}
