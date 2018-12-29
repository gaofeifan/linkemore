package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserGroupDetailClusterMapper;
import cn.linkmore.account.dao.cluster.UserGroupInputClusterMapper;
import cn.linkmore.account.dao.master.UserGroupDetailMasterMapper;
import cn.linkmore.account.dao.master.UserGroupInputMasterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.UserGroupDetail;
import cn.linkmore.account.entity.UserGroupInput;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResGroupUser;
import cn.linkmore.account.response.ResUserGroupInput;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.UserGroupInputService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class UserGroupInputServiceImpl implements UserGroupInputService {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserGroupInputMasterMapper userGroupInputMasterMapper;
	
	@Autowired
	private UserGroupInputClusterMapper userGroupInputClusterMapper;
	
	@Autowired
	private UserGroupDetailClusterMapper userGroupDetailClusterMapper;
	
	@Autowired
	private UserGroupDetailMasterMapper userGroupDetailMasterMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleMarkManageService vehicleMarkManageService;
	@Autowired
	private VehicleMarkManageMasterMapper vehicleMarkManageMasterMapper;
	
	private UserGroupInput reqToEntity(ReqUserGroupInput record) {
		UserGroupInput userGroupInput=new UserGroupInput();
		userGroupInput = ObjectUtils.copyObject(record, userGroupInput);
		return userGroupInput;
	}
	private ResUserGroupInput entityToRes(UserGroupInput record) {
		ResUserGroupInput resUserGroupInput=new ResUserGroupInput();
		resUserGroupInput = ObjectUtils.copyObject(record, resUserGroupInput);
		return resUserGroupInput;
	}
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userGroupInputMasterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return userGroupInputMasterMapper.deleteByIds(ids);
	}
	
	@Override
	public int deleteAndUserByIds(List<Long> ids) {
		int count = userGroupDetailMasterMapper.deleteByInputIds(ids);
		if (count>0) {
			userGroupInputMasterMapper.deleteByIds(ids);
		}
		return count;
	}
	
	@Override
	public int deleteByGroupIds(List<Long> ids) {
		return userGroupInputMasterMapper.deleteByGroupIds(ids);
	}
	
	@Override
	public int insertByUserIds(Map<String, Object> map) {
		return userGroupInputMasterMapper.insertByUserIds(map);
	}
	
	@Override
	public int insert(ReqUserGroupInput record) {
		return userGroupInputMasterMapper.insert(reqToEntity(record));
	}

	@Override
	public int insertSelective(ReqUserGroupInput record) {
		return userGroupInputMasterMapper.insertSelective(reqToEntity(record));
	}

	@Override
	public int updateByPrimaryKeySelective(ReqUserGroupInput record) {
		return userGroupInputMasterMapper.updateByPrimaryKeySelective(reqToEntity(record));
	}

	@Override
	public int updateByPrimaryKey(ReqUserGroupInput record) {
		return userGroupInputMasterMapper.updateByPrimaryKey(reqToEntity(record));
	}

	@Override
	public ResUserGroupInput selectByPrimaryKey(Long id) {
		return entityToRes(userGroupInputClusterMapper.selectByPrimaryKey(id));
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
		Integer count = this.userGroupInputClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResUserGroupInput> list = this.userGroupInputClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	@Override
	public boolean exists(ReqUserGroupInput reqUserGroupInput) {
		UserGroupInput userGroupInput = userGroupInputClusterMapper.findByPlate(reqUserGroupInput);
		return userGroupInput != null ? true:false;
	}
	
	@Override
	public boolean syncByUserGroupId(Long userGroupId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userGroupId", userGroupId);
		return sync(param);
	}

	@Override
	public boolean syncByPlate(String plate) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("plate", plate);
		return sync(param);
	}

	@Override
	public boolean syncByUserId(Long userId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		return sync(param);
	}
	
	@Override
	public boolean syncByUserIdAndPlate(Long userId,String plate) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("plate", plate);
		return sync(param);
	}
	
	public boolean sync(Map<String,Object> param) {
		Long userId=0L;
		ResVechicleMark resVechicleMark=null;
		Map<String,Object> vehicleMarkParam=new HashMap<String,Object>();
		
		List<UserGroupInput> listInput = userGroupInputClusterMapper.findList(param);
		if (CollectionUtils.isNotEmpty(listInput)) {
			
			//有手机号的，创建新用户,为用户增加车牌
			for(UserGroupInput userGroupInput:listInput) {
				if (StringUtils.isNotEmpty(userGroupInput.getMobile()) ) {
					userId = userService.getUserIdByMobile(userGroupInput.getMobile());
					vehicleMarkParam.put("userId", userId);
					vehicleMarkParam.put("plateNo", userGroupInput.getPlate());
					resVechicleMark = vehicleMarkManageService.findByPlateNo(vehicleMarkParam);
					if(resVechicleMark == null) {
						VehicleMarkManage vehicleMarkManage=new VehicleMarkManage();
						vehicleMarkManage.setCreateTime(new Date());
						vehicleMarkManage.setUpdateTime(new Date());
						vehicleMarkManage.setVehUserId(String.valueOf(userId));
						vehicleMarkManage.setVehMark(userGroupInput.getPlate());
						vehicleMarkManageMasterMapper.insert(vehicleMarkManage);
					}
				}
			}
		}
			List<UserGroupDetail> listUserDetail = userGroupDetailClusterMapper.findList(param);
			List<UserGroupDetail> listSyncDetail = userGroupDetailClusterMapper.findSyncList(param);
			log.info("sync user group old list size={} , new list size={}",listUserDetail.size(),listSyncDetail.size());
			
			//删除
			if (CollectionUtils.isNotEmpty(listUserDetail)) {
				List <Long> ids =new ArrayList<Long>();
				for(UserGroupDetail userGroupDetail : listUserDetail) {
					if(!existGroupUser(listSyncDetail,userGroupDetail) ) {
						ids.add(userGroupDetail.getId());
					}
				}
				if(ids !=null && ids.size()>0) {
					userGroupDetailMasterMapper.deleteByIds(ids);
				}
			}

			//新增
			if (CollectionUtils.isNotEmpty(listSyncDetail)) {
				for(UserGroupDetail userGroupDetail : listSyncDetail) {
					if(!existGroupUser(listUserDetail,userGroupDetail) ) {
						userGroupDetail.setCreateTime(new Date());
						userGroupDetail.setCreateUserId(88L);
						userGroupDetailMasterMapper.insert(userGroupDetail);
					}
				}
			}
		
		
		return true;
	}
	


	private boolean existGroupUser(List<UserGroupDetail> listUserDetail,UserGroupDetail userGroupDetail) {
		if (CollectionUtils.isNotEmpty(listUserDetail)) {
			for (UserGroupDetail userDetail : listUserDetail) {
				if (userDetail.getUserGroupId().equals(userGroupDetail.getUserGroupId())
					&& 	userDetail.getUserId().equals(userGroupDetail.getUserId()) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public ViewPage pageUserByNotInUserGroup(ViewPageable pageable) {
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
		Integer count = this.userGroupDetailClusterMapper.pageUserByNotInUserGroupCount(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResGroupUser> list = this.userGroupDetailClusterMapper.pageUserByNotInUserGroup(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

}
