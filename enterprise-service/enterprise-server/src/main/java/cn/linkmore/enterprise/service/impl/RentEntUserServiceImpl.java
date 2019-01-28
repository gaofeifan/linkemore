package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.cluster.RentEntUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentUserMasterMapper;
import cn.linkmore.enterprise.dao.master.RentEntUserMasterMapper;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.entity.RentEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.enterprise.service.RentEntService;
import cn.linkmore.enterprise.service.RentEntStallService;
import cn.linkmore.enterprise.service.RentEntUserService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class RentEntUserServiceImpl implements RentEntUserService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private RentEntUserClusterMapper rentEntUserClusterMapper;
	@Resource
	private RentEntUserMasterMapper rentEntUserMasterMapper;
	@Resource
	private RentEntStallService rentEntStallService;
	@Resource
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Resource
	private EntRentUserService entRentUserService;
	@Resource
	private RentEntService rentEntService;
	@Resource
	private UserClient userClient;
	@Resource
	private VehicleMarkClient vehicleMarkClient;
	
	@Resource
	private EntRentUserClusterMapper entRentUserClusterMapper;
	@Resource
	private EntRentUserMasterMapper entRentUserMasterMapper;
	
	@Override
	public RentEntUser selectByPrimaryKey(Long rentEntId) {
		return this.rentEntUserClusterMapper.findById(rentEntId);
	}
	
	@Override
	public void save(ReqRentEntUser user) {
		RentEntUser entUser = ObjectUtils.copyObject(user, new RentEntUser());
		if( StringUtils.isNotEmpty(user.getMobile())) {
			Long mobile = userClient.getUserIdByMobile(user.getMobile());
			if(mobile != null) {
				entUser.setUserId(mobile);
			}
		}else {
			
		}
		this.rentEntUserMasterMapper.insert(entUser);
	}
	
	@Override
	public void update(ReqRentEntUser user) {
		if( StringUtils.isNotEmpty(user.getMobile())) {
			Long mobile = userClient.getUserIdByMobile(user.getMobile());
			if(mobile != null) {
				user.setUserId(mobile);
			}
		}else {
			
		}
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
	
	@Override
	public void syncRentStallByCompanyId(Long companyId) {
		log.info("sync rent com user byCompanyId:{} ",companyId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		syncRentStall(param);
	}

	@Override
	public void syncRentStallByUserId(Long userId) {
		log.info("sync rent com user byUserId:{} ",userId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		syncRentStall(param);
	}
	
	@Override
	public void syncRentStall() {
		syncRentStall(new HashMap<String, Object>());
	}
	
	
	public void syncRentStall(Map<String, Object> param) {
		
		List<ResEnterprise> enterpriseList = this.enterpriseClusterMapper.findAll();
		Map<Long,String> enterMap = new HashMap<Long,String>();
		if(CollectionUtils.isNotEmpty(enterpriseList)) {
			for(ResEnterprise enter: enterpriseList) {
				enterMap.put(enter.getId(), enter.getName());
			}
		}
		param.put("type", 1);
		
		//自动创建用户和为用户创建车牌
		if(param.get("companyId") != null) {
			List<RentEntUser> listRentEntUser = rentEntUserClusterMapper.findList(param);
			if (CollectionUtils.isNotEmpty(listRentEntUser)) {
				Long userId=0L;
				for (RentEntUser stall : listRentEntUser) {
					if (StringUtils.isNotEmpty(stall.getMobile()) ) {
						userId = userClient.getUserIdByMobile(stall.getMobile());
						ReqVehicleMark vehicleMarkManage=new ReqVehicleMark();
						vehicleMarkManage.setUserId(userId);
						vehicleMarkManage.setVehMark(stall.getPlate());
						vehicleMarkManage.setNewpl(stall.getPlate());
						vehicleMarkClient.insertByNoRepeat(vehicleMarkManage);
					}
				}
			}
		}
		
		//开使比对
		List<EntRentUser> oldRentUserList = entRentUserClusterMapper.findCompanyUserList(param);
		List<EntRentUser> newRentUserList = entRentUserClusterMapper.findRentComUserList(param);
		log.info("sync rent com user old list size={} , new list size={}",oldRentUserList.size(),newRentUserList.size());	

		//新记录增加
		List<EntRentUser> entRentUser = new ArrayList<EntRentUser>();
		if (CollectionUtils.isNotEmpty(newRentUserList)) {
			for (EntRentUser stall : newRentUserList) {
				if (! existRentUser(oldRentUserList,stall)) {
					stall.setType((short) 1);
					stall.setCreateTime(new Date());
					stall.setUpdateTime(new Date());
					stall.setUpdateUserId(stall.getCreateUserId());
					stall.setUpdateUserName(stall.getCreateUserName());
					if(stall.getCreateEntId()!= null) {
						//同步企业id
						stall.setEntId(stall.getCreateEntId());
						stall.setEntName(enterMap.get(stall.getEntId()));
					}
					entRentUser.add(stall);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(entRentUser)) {
			log.info("add the new rent com user size={},data={}", entRentUser.size(),JSON.toJSON(entRentUser));
			entRentUserMasterMapper.saveBatch(entRentUser);
		}
		
		//不存在的记录删除
		List<Long> ids=new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(oldRentUserList)) {
			for (EntRentUser stall : oldRentUserList) {
				if (! existRentUser(newRentUserList,stall)) {
					ids.add(stall.getId());
				}
			}
		}
		if (CollectionUtils.isNotEmpty(ids)) {
			log.info("delete the rent com user size={},data={}", ids.size(),JSON.toJSON(ids) );
			entRentUserMasterMapper.delete(ids);
		}
		
		log.info("sync rent com user finished.");
	}
	
	private boolean existRentUser(List<EntRentUser> rentUserList,EntRentUser entRentUser) {
		if (CollectionUtils.isNotEmpty(rentUserList)) {
			for (EntRentUser userStall : rentUserList) {
				if(userStall.getPreId() != null &&  entRentUser.getPreId() != null
					&& userStall.getCompanyId() != null &&  entRentUser.getCompanyId() != null
					&& userStall.getStallId() != null &&  entRentUser.getStallId() != null
					&& userStall.getUserId() != null &&  entRentUser.getUserId() != null
				){			
					if(userStall.getPreId().longValue() == entRentUser.getPreId().longValue()
							&& userStall.getCompanyId().longValue() == entRentUser.getCompanyId().longValue()
							&& userStall.getStallId().longValue() == entRentUser.getStallId().longValue()
							&& userStall.getUserId().longValue() == entRentUser.getUserId().longValue()
							&& StringUtils.equalsIgnoreCase(userStall.getPlate(), entRentUser.getPlate())
							) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void syncRentPersonalUserStallByPlate(String plate) {
		log.info("sync rent Personal user plate:{} ",plate);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("plate", plate);
		syncRentPersonalUserStall(param);
	}
	
	@Override
	public void syncRentPersonalUserStallByUserId(Long userId) {
		log.info("sync rent Personal user userId:{} ",userId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		syncRentPersonalUserStall(param);
	}
	
	@Override
	public void syncRentPersonalUserStall() {
		syncRentPersonalUserStall(new HashMap<String, Object>());
	}
	/**
	 * 同步个人长租车位
	 * @param param
	 */
	public void syncRentPersonalUserStall(Map<String, Object> param) {
		
		List<EntRentUser> oldRentUserList = entRentUserClusterMapper.findPersonalUserList(param);
		
		//自动创建用户和为用户创建车牌
		if (CollectionUtils.isNotEmpty(oldRentUserList)) {
			Long userId=0L;
			for (EntRentUser stall : oldRentUserList) {
				if (StringUtils.isNotEmpty(stall.getMobile()) ) {
					userId = userClient.getUserIdByMobile(stall.getMobile());
					ReqVehicleMark vehicleMarkManage=new ReqVehicleMark();
					vehicleMarkManage.setUserId(userId);
					vehicleMarkManage.setVehMark(stall.getPlate());
					vehicleMarkManage.setNewpl(stall.getPlate());
					vehicleMarkClient.insertByNoRepeat(vehicleMarkManage);
				}
			}
		}

		
		List<EntRentUser> newRentUserList = entRentUserClusterMapper.findRentPersonalUserList(param);
		log.info("sync rent Personal user old list size={} , new list size={}",oldRentUserList.size(),newRentUserList.size());

		//新记录增加
		List<EntRentUser> entRentUser = new ArrayList<EntRentUser>();
		if (CollectionUtils.isNotEmpty(newRentUserList)) {
			for (EntRentUser stall : newRentUserList) {
				EntRentUser oldStall=existRentPersonalUser(oldRentUserList,stall);
				if(oldStall != null) {
					if(oldStall.getUserId() != null) {
						//insert
						if (!oldStall.getUserId().equals(stall.getUserId())) {
							stall.setType((short) 0);
							entRentUser.add(stall);
						}
					}else {
						//update
						stall.setId(oldStall.getId());
						entRentUserMasterMapper.updateByIdSelective(stall);
						oldStall.setUserId(stall.getUserId());
					}
				}else {
					stall.setType((short) 0);
					entRentUser.add(stall);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(entRentUser)) {
			log.info("add the new rent Personal user size={},data={}", entRentUser.size(),JSON.toJSON(entRentUser));
			entRentUserMasterMapper.saveBatch(entRentUser);
		}
		
		/**
		 * 用户从app上删除车牌的，将userid置为null
		 */
		if (CollectionUtils.isNotEmpty(oldRentUserList)) {
			for (EntRentUser stall : oldRentUserList) {
				EntRentUser newStall=existRentPersonalUser(newRentUserList,stall);
				if(newStall == null) {
					//stall.setUserId(null);
					//System.out.println("null");
					entRentUserMasterMapper.updateUserIdNULLById(stall);
				//	entRentUserMasterMapper.updateByIdSelective(stall);
				}
			}
		}
		
		
	}

	/**
	 * 判断是否存在 
	 * @param rentUserList
	 * @param entRentUser
	 * @return
	 */
	private EntRentUser existRentPersonalUser(List<EntRentUser> rentUserList,EntRentUser entRentUser) {
		EntRentUser retEntRentUser=null;
		if (CollectionUtils.isNotEmpty(rentUserList)) {
			for (EntRentUser userStall : rentUserList) {
				if(userStall.getPreId() != null &&  entRentUser.getPreId() != null
					&& userStall.getStallId() != null &&  entRentUser.getStallId() != null
					//&& userStall.getUserId() != null &&  entRentUser.getUserId() != null
				){
					if(userStall.getPreId().longValue() == entRentUser.getPreId().longValue()
							&& userStall.getStallId().longValue() == entRentUser.getStallId().longValue()
							&& StringUtils.equalsIgnoreCase(userStall.getPlate(), entRentUser.getPlate())
							) {
						
						if (userStall.getUserId() != null && userStall.getUserId().longValue() == entRentUser.getUserId().longValue()) {
							return userStall;
						}else {
							retEntRentUser=userStall;
						}
					}
				}
			}
		}
		return retEntRentUser;
	}

}
