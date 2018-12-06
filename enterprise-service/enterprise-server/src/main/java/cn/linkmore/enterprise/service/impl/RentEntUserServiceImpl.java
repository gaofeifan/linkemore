package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.RentEntUserClusterMapper;
import cn.linkmore.enterprise.dao.master.RentEntUserMasterMapper;
import cn.linkmore.enterprise.entity.RentEnt;
import cn.linkmore.enterprise.entity.RentEntStall;
import cn.linkmore.enterprise.entity.RentEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.enterprise.service.EnterpriseService;
import cn.linkmore.enterprise.service.RentEntService;
import cn.linkmore.enterprise.service.RentEntStallService;
import cn.linkmore.enterprise.service.RentEntUserService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class RentEntUserServiceImpl implements RentEntUserService {

	@Resource
	private RentEntUserClusterMapper rentEntUserClusterMapper;
	@Resource
	private RentEntUserMasterMapper rentEntUserMasterMapper;
	@Resource
	private RentEntStallService rentEntStallService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private EntRentUserService entRentUserService;
	@Resource
	private RentEntService rentEntService;
	@Resource
	private UserClient userClient;
	@Override
	public RentEntUser selectByPrimaryKey(Long rentEntId) {
		return this.rentEntUserClusterMapper.findById(rentEntId);
	}
	
	@Override
	public void save(ReqRentEntUser user) {
		RentEntUser entUser = ObjectUtils.copyObject(user, new RentEntUser());
/*
		1 手机号不为空
			系统中有这个用户 -> 关联车牌
			系统中无这个用户 -> 创建用户
		2 手机号为空
			系统中有这个车牌，关联到该用户
			系统中无这个车牌
*/
		if( ! StringUtils.isNotEmpty(user.getMobile())) {
			Long mobile = userClient.getUserIdByMobile(user.getMobile());
			if(mobile != null) {
				entUser.setUserId(mobile);
			}
			//entUser.setUserName(userName);
		}else {
			/*
			RentEntUser u = this.rentEntUserClusterMapper.findMaxId();
			// 临时判断 需求完善在修改
			if(u.getId() > 100000000) {
				entUser.setId(u.getId() + 1);
			}else {
				entUser.setId(100000001L);
			}
			*/
		}
		this.rentEntUserMasterMapper.insert(entUser);
		/*
		List<RentEntStall> list = this.rentEntStallService.stallListCompany(entUser.getRentComId());
		RentEnt rentEnt = this.rentEntService.findById(entUser.getRentComId());
		ResEnterprise enterprise = this.enterpriseService.findById(rentEnt.getCreateUserId());
		ReqRentUser record;
		
		List<ReqRentUser> rus = new ArrayList<>();
		for (RentEntStall rentEntStall : list) {
			record = new ReqRentUser();
			record.setEndDate(DateUtils.converter(rentEnt.getEndTime(), null));
			record.setStartDate(DateUtils.converter(rentEnt.getStartTime(), null));
			record.setEntId(rentEnt.getCreateUserId());
			record.setStallId(rentEntStall.getStallId());
			record.setStallName(rentEntStall.getStallName());
			record.setPreId(rentEntStall.getPreId());
			record.setMobile(entUser.getMobile());
			record.setUserId(entUser.getId());
			record.setPlate(entUser.getPlate());
			record.setRealname(entUser.getUserName());
			record.setEntName(enterprise.getName());
			record.setPreName(rentEntStall.getPreName());
			record.setCompanyId(rentEnt.getId());
			record.setCompanyName(rentEnt.getCompanyName());
			rus.add(record);
		}
		this.entRentUserService.saveBatch(rus);
		*/
		
	}
	
	@Override
	public void update(ReqRentEntUser user) {
		this.rentEntUserMasterMapper.updateByPrimaryKey(ObjectUtils.copyObject(user, new RentEntUser()));
	}
	@Override
	public void deleteIds(List<Long> ids) {
		this.rentEntUserMasterMapper.deleteIds(ids);
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
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.rentEntUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<RentEntUser> list = this.rentEntUserClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void saveI(ReqRentUser ent) {
		Long mobile = userClient.getUserIdByMobile(ent.getMobile());
		ent.setUserId(mobile);
		this.entRentUserService.save(ent);
	}

	@Override
	public void updateI(ReqRentUser ent) {
		this.entRentUserService.update(ent);
	}

	@Override
	public void deleteIdsI(List<Long> ids) {
		this.entRentUserService.delete(ids);
	}

	@Override
	public ViewPage findPageI(ViewPageable pageable) {
		return this.entRentUserService.findList(pageable);
	}
	
	public boolean exists(ReqRentEntUser ent) {
		RentEntUser rentEntUser = rentEntUserClusterMapper.findByPlate(ent);
		return rentEntUser != null ? true:false;
	}

}
