package cn.linkmore.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.BaseAppVersionClusterMapper;
import cn.linkmore.common.dao.cluster.UserVersionClusterMapper;
import cn.linkmore.common.dao.master.BaseAppVersionMasterMapper;
import cn.linkmore.common.dao.master.UserVersionMasterMapper;
import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.entity.Common;
import cn.linkmore.common.entity.UserVersion;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.service.BeanVersionService;
import cn.linkmore.common.service.CommonService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
/**
 * 版本实现类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class BeanVersionServiceImpl implements BeanVersionService {

	@Resource
	private RedisService redisService;
	@Resource
	private CommonService commonService;
	@Resource
	private UserVersionClusterMapper versionClusterMapper;
	@Resource
	private UserVersionMasterMapper versionMasterMapper;
	@Resource
	private BaseAppVersionClusterMapper baseAppVersionClusterMapper;
	@Resource
	private BaseAppVersionMasterMapper baseAppVersionMasterMapper;
	
	@Override
	public ResVersionBean currentAppVersion(Integer appType) {
		Map<String, Object> map = new HashMap<>();
		map.put("appType", appType);
		map.put("status", 1);
		List<ResVersionBean> res = this.baseAppVersionClusterMapper.findByTypeAnStatus(map);
		return res.get(0);
	}
	
	public List<BaseAppVersion> findList(Common common){
		@SuppressWarnings("unchecked")
		List<BaseAppVersion> list = (List<BaseAppVersion>) commonService.selectList(common );
		return list;
	}

	@Override
	public void report(ReqVersion vrb) {
		boolean falg = false;
		UserVersion version = this.versionClusterMapper.findById(vrb.getUserId());
		if(version != null) {
			falg = true;
		}
		version = ObjectUtils.copyObject(vrb, new UserVersion());
		version.setCommitTime(new Date());
		if(falg) {
			this.versionMasterMapper.updateById(version);
			return;
		}
		this.versionMasterMapper.insert(version);
	}

	@Override
	public void saveApp(ReqAppVersion version) {
		version.setCreateTime(new Date());
		this.baseAppVersionMasterMapper.insertReq(version);
	}

	@Override
	public void updateApp(ReqAppVersion version) {
		version.setUpdateTime(new Date());
		this.baseAppVersionMasterMapper.updateReq(version);
	}

	@Override
	public void deleteAppById(List<Long> ids) {
		this.baseAppVersionMasterMapper.deleteAppById(ids);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.baseAppVersionClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<BaseAppVersion> list = this.baseAppVersionClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public Integer check(ReqCheck check) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", check.getProperty());
		param.put("value", check.getValue());
		param.put("id", check.getId());
		return this.baseAppVersionClusterMapper.check(param); 
	}

	@Override
	public ViewPage findUserPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.versionClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<UserVersion> list = this.versionClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public ResVersionBean current(Integer source) {
		return this.currentAppVersion(source);
	}

	@Override
	public void report(cn.linkmore.common.controller.app.request.ReqVersion vrb, HttpServletRequest request) {
//		String key = TokenUtil.getKey(request);
//	    CacheUser user =  (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
//		vrb.setUserId(user.getId());
		ReqVersion version = ObjectUtils.copyObject(vrb, new ReqVersion());
		this.report(version);
	}
	
}

