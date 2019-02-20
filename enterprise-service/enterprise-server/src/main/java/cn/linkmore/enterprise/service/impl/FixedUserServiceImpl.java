package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.FixedUserClusterMapper;
import cn.linkmore.enterprise.dao.master.FixedUserMasterMapper;
import cn.linkmore.enterprise.entity.FixedUserPick;
import cn.linkmore.enterprise.entity.ReqFixedUser;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.enterprise.service.FixedUserService;
import cn.linkmore.util.DomainUtil;

@Service
public class FixedUserServiceImpl implements FixedUserService {

	@Autowired
	private FixedUserClusterMapper fixedUserClusterMapper;

	@Autowired
	private FixedUserMasterMapper fixedUserMasterMapper;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			List<ViewFilter> filters = pageable.getFilters();
			if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
				param.put(pageable.getSearchProperty(), pageable.getSearchValue());
			}
			if (filters != null && filters.size() > 0) {
				for (ViewFilter filter : filters) {
					param.put(filter.getProperty(), filter.getValue());
				}
			}
			if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
				param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
				param.put("direction", pageable.getOrderDirection());
			}

			Integer count = this.fixedUserClusterMapper.count(param);
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			List<ReqFixedUser> list = this.fixedUserClusterMapper.findPage(param);
			return new ViewPage(count, pageable.getPageSize(), list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void pick(ReqFixedUserPick reqFixedUserPick) {
		FixedUserPick  frx = new FixedUserPick();
		frx.setCreateTime(new Date());
		frx.setCreateUserId(reqFixedUserPick.getCreateUserId());
		frx.setFixedId(reqFixedUserPick.getFixedId());
		frx.setUserId(reqFixedUserPick.getUserId());
		if (reqFixedUserPick.getState() == 1) {
			this.fixedUserMasterMapper.delete(frx);
		} else if (reqFixedUserPick.getState() == 0) {
			this.fixedUserMasterMapper.save(frx);
		}
	}

}
