package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandUserMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandUser;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
import cn.linkmore.enterprise.response.ResBrandUser;
import cn.linkmore.enterprise.service.EntBrandUserService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * 授权品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandUserServiceImpl implements EntBrandUserService {
	@Resource
	private EntBrandUserMasterMapper entBrandUserMasterMapper;
	
	@Resource
	private EntBrandUserClusterMapper entBrandUserClusterMapper;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
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
		Integer count = this.entBrandUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBrandUser> list = this.entBrandUserClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResBrandUser> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(ReqEntBrandUser record) {
		EntBrandUser brandUser = new EntBrandUser();
		brandUser = ObjectUtils.copyObject(record, brandUser);
		return entBrandUserMasterMapper.save(brandUser);
	}

	@Override
	public int update(ReqEntBrandUser record) {
		EntBrandUser brandUser = new EntBrandUser();
		brandUser = ObjectUtils.copyObject(record, brandUser);
		return entBrandUserMasterMapper.update(brandUser);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandUserClusterMapper.check(param);
	}

	@Override
	public int delete(List<Long> ids) {
		return entBrandUserMasterMapper.delete(ids);
	}
	
}
