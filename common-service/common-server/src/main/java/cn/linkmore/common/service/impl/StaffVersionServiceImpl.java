package cn.linkmore.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.controller.staff.response.ResStaffAppVersion;
import cn.linkmore.common.dao.cluster.StaffAppVersionClusterMapper;
import cn.linkmore.common.dao.cluster.UserVersionClusterMapper;
import cn.linkmore.common.dao.cluster.WyAppVersionClusterMapper;
import cn.linkmore.common.dao.master.StaffAppVersionMasterMapper;
import cn.linkmore.common.dao.master.UserVersionMasterMapper;
import cn.linkmore.common.dao.master.WyAppVersionMasterMapper;
import cn.linkmore.common.entity.Common;
import cn.linkmore.common.entity.StaffAppVersion;
import cn.linkmore.common.entity.UserVersion;
import cn.linkmore.common.entity.WyAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqStaffAppVersion;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.request.ReqWyAppVersion;
import cn.linkmore.common.response.ResWyAppVersion;
import cn.linkmore.common.service.CommonService;
import cn.linkmore.common.service.StaffVersionService;
import cn.linkmore.common.service.WyVersionService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
/**
 * 物业版版本实现类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class StaffVersionServiceImpl implements StaffVersionService {

	@Resource
	private RedisService redisService;
	@Resource
	private CommonService commonService;
	@Resource
	private UserVersionClusterMapper versionClusterMapper;
	@Resource
	private UserVersionMasterMapper versionMasterMapper;
	@Resource
	private StaffAppVersionClusterMapper staffAppVersionClusterMapper;
	@Resource
	private StaffAppVersionMasterMapper staffAppVersionMasterMapper;
	@Resource
	private UserStaffClient userStaffClient;
	@Override
	public ResStaffAppVersion currentAppVersion(Integer appType,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("appType", appType);
		map.put("status", 1);
		List<cn.linkmore.common.response.ResStaffAppVersion> res = this.staffAppVersionClusterMapper.findByTypeAnStatus(map);
		if(res != null && res.size() > 0) {
			return ObjectUtils.copyObject(res.get(0),new ResStaffAppVersion());
		}
		return null;
	}
	
	public List<StaffAppVersion> findList(Common common){
//		List<WyAppVersion> list = (List<WyAppVersion>) commonService.selectList(common );
		return null;
	}

	@Override
	public void report(cn.linkmore.common.controller.app.request.ReqVersion vrb, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
	    CacheUser user =  (CacheUser) this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		ReqVersion reqVersion = ObjectUtils.copyObject(vrb, new ReqVersion());
		reqVersion.setUserId(user.getId());
		boolean falg = false;
		Map<String, Object> map = new HashMap<>();
		map.put("userId", user.getId());
		map.put("system", 3);
		UserVersion version = this.versionClusterMapper.findById(map);
		if(version != null) {
			falg = true;
		}
		version = ObjectUtils.copyObject(reqVersion, new UserVersion());
		version.setCommitTime(new Date());
		version.setSystem(3);
		if(falg) {
			this.versionMasterMapper.updateById(version);
			return;
		}
		this.versionMasterMapper.insert(version);
	}

	@Override
	public void saveApp(ReqStaffAppVersion version) {
		version.setCreateTime(new Date());
		this.staffAppVersionMasterMapper.insertReq(version);
	}

	@Override
	public void updateApp(ReqStaffAppVersion version) {
		version.setUpdateTime(new Date());
		this.staffAppVersionMasterMapper.updateReq(version);
	}

	@Override
	public void deleteAppById(List<Long> ids) {
		this.staffAppVersionMasterMapper.deleteAppById(ids);
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
		Integer count = this.staffAppVersionClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<StaffAppVersion> list = this.staffAppVersionClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public Integer check(ReqCheck check) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", check.getProperty());
		param.put("value", check.getValue());
		param.put("id", check.getId());
		return this.staffAppVersionClusterMapper.check(param); 
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
}

