package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandUserMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandUser;
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
	
	@Autowired
	private UserClient userClient;

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
		ResUser user = getUser(brandUser.getMobile());
		if(user != null) {
			brandUser.setUserId(user.getId());
		}
		return entBrandUserMasterMapper.save(brandUser);
	}
	
	private ResUser getUser(String phone) {
		ResUser user = null;
		user = this.userClient.getUserByUserName(phone);
		if (user != null) {
			return user;
		} else {
			user = new ResUser();
			user.setMobile(phone);
			user.setUsername(phone);
			user.setStatus("0");
			user.setUserType("1");
			user.setCreateTime(new Date());
			return this.userClient.save(user);
		}
	}

	@Override
	public int update(ReqEntBrandUser record) {
		EntBrandUser brandUser = new EntBrandUser();
		brandUser = ObjectUtils.copyObject(record, brandUser);
		return entBrandUserMasterMapper.update(brandUser);
	}

	@Override
	public int delete(List<Long> ids) {
		return entBrandUserMasterMapper.delete(ids);
	}

	@Override
	public Integer check(ReqEntBrandUser record) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("entId", record.getEntId());
		param.put("mobile", record.getMobile());
		return this.entBrandUserClusterMapper.check(param);
	}

	@Override
	public int insertBatch(List<ReqEntBrandUser> reqUserList) {
		return this.entBrandUserMasterMapper.insertBatch(reqUserList);
	}
	
}
