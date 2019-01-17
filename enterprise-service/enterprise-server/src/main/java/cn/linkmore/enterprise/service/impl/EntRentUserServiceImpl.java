/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentUserMasterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEntRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.enterprise.service.RentEntUserService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.StringUtil;

/**
 * 长租用户信息
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntRentUserServiceImpl implements EntRentUserService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntRentUserMasterMapper entRentUserMasterMapper;
	@Autowired
	private EntRentUserClusterMapper entRentUserClusterMapper;
	@Autowired
	private EntRentedRecordMasterMapper entRentedRecordMasterMapper;
	
	@Autowired
	private EnterpriseClusterMapper enterpriseClusterMapper;
	@Autowired
	private EntPrefectureClusterMapper  entPrefectureClusterMapper;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private RentEntUserService rentEntUserService;
	
	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;

	@Override
	public int saveEntRentUser(Long entId, Long entPreId, Long stallId, String mobile, String realname, String plate) {
		
		ResEnterprise resEnterprise =this.enterpriseClusterMapper.findById(entId);
		if(resEnterprise == null){
			return 0;
		}
		EntPrefecture  entPrefecture= this.entPrefectureClusterMapper.findById(entPreId);
		if(entPrefecture == null){
			return 0;
		}
		if(StringUtil.isBlank(mobile)){
			return 0;
		}
		EntRentUser record = new EntRentUser();
		record.setEntId(entId);
		record.setEntPreId(entPreId);
		record.setStallId(stallId);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setPlate(plate);
		
		Long userId = userClient.getUserIdByMobile(mobile);
		if(userId == null){
			logger.info("{}手机号不存在",mobile);
		}
		record.setUserId(userId);
		return this.entRentUserMasterMapper.save(record);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntRentUserService#deleteEntRentUser(java.lang.Long)
	 */
	@Override
	public int deleteEntRentUser(Long id) {
		return this.entRentUserMasterMapper.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see cn.linkmore.enterprise.service.EntRentUserService#updateEntRentUser(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateEntRentUser(Long id, String mobile, String realname, String plate) {
		EntRentUser record = new EntRentUser();
		if(id == null){
			return 0;
		}
		if(StringUtil.isBlank(mobile)){
			return 0;
		}
		record.setId(id);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setPlate(plate);
		Long userId = userClient.getUserIdByMobile(mobile);
		if(userId == null){
			logger.info("{}手机号不存在",mobile);
		}
		record.setUserId(userId);
		return this.entRentUserMasterMapper.updateByIdSelective(record);
	}

	@Override
	public void delete(List<Long> ids) {
		Map<String,Object> map = null;
		for(Long id : ids) {
			EntRentUser entRentUser = entRentUserClusterMapper.findById(id);
			if(entRentUser != null) {
				map = new HashMap<String,Object>();
				map.put("userId", entRentUser.getUserId());
				map.put("stallId", entRentUser.getStallId());
				this.entRentedRecordMasterMapper.updateStatus(map);
			}
		}
		this.entRentUserMasterMapper.delete(ids);
	}

	@Override
	public ViewPage findList(ViewPageable pageable) {
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
		Integer count = this.entRentUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResEntRentUser> list = this.entRentUserClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(ReqRentUser user) {
		Long userId = userClient.getUserIdByMobile(user.getMobile());
		/*
		if(userId != null) {
			user.setUserId(userId);
		}
		*/
		user.setStartTime(DateUtils.convert(user.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
		user.setEndTime(DateUtils.convert(user.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
		this.entRentUserMasterMapper.saveReq(user);
		rentEntUserService.syncRentPersonalUserStallByPlate(user.getPlate());
		
	}

	@Override
	public void update(ReqRentUser user) {
		Long userId = userClient.getUserIdByMobile(user.getMobile());
		/*
		if(userId != null) {
			user.setUserId(userId);
		}
		*/
		user.setStartTime(DateUtils.convert(user.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
		user.setEndTime(DateUtils.convert(user.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
		this.entRentUserMasterMapper.updateReq(user);
		rentEntUserService.syncRentPersonalUserStallByPlate(user.getPlate());
	}

	@Override
	public List<EntRentUser> findAll() {
		return this.entRentUserClusterMapper.findAll();
	}

	@Override
	public EntRentUser findByStallId(Long id) {
		return this.entRentUserClusterMapper.findByStallId(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entRentUserClusterMapper.check(param);
	}

	@Override
	public List<ResEntRentUser> findUsedStall() {
		return this.entRentUserClusterMapper.findUsedStall();
	}


	@Override
	public void saveBatch(List<ReqRentUser> rus) {
		//this.entRentUserMasterMapper.saveBatch(rus);
	}
	
	/**
	 * 根据车区id和用户id查询该用户是否拥有长租车位权限
	 * @param param
	 * @return
	 */
	public Boolean checkExist(Map<String,Object> param){
		boolean flag = false;
		Long userId = Long.valueOf(param.get("userId").toString());
		Long preId = Long.valueOf(param.get("preId").toString());
		String plate = null;
		if(param.get("plate") != null) {
			plate = (String)param.get("plate");
		}
		
		List<EntOwnerStall> ownStallList = ownerStallClusterMapper.findStall(userId);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>check the use have the privilage of rent stall param = {} result = {}",JSON.toJSON(param),JSON.toJSON(ownStallList));
		if(preId == 0L && plate == null) {
			if(CollectionUtils.isNotEmpty(ownStallList)) {
				return true;
			}
		}else if(preId != 0L && plate == null){
			if(CollectionUtils.isNotEmpty(ownStallList)) {
				for(EntOwnerStall ownerStall: ownStallList) {
					if(preId.equals(ownerStall.getPreId())) {
						flag = true;
						break;
					}
				}
			}
		}else if(preId == 0L && plate != null) {
			if(CollectionUtils.isNotEmpty(ownStallList)) {
				for(EntOwnerStall ownerStall: ownStallList) {
					if(plate.equals(ownerStall.getPlate())) {
						flag = true;
						break;
					}
				}
			}
		}
		/*logger.info("company param = {}",JSON.toJSON(param));
		List<EntRentUser> oldRentUserList = entRentUserClusterMapper.findComUserList(param);
		logger.info("company oldRentUserList = {}",JSON.toJSON(oldRentUserList));
		Boolean flag = false;
		if(CollectionUtils.isNotEmpty(oldRentUserList)) {
			flag = true;
		}
		logger.info("company flag = {}",JSON.toJSON(flag));*/
		return flag;
	}

	@Override
	public Boolean exists(Map<String, Object> param) {
		param.put("type", 0);
		logger.info("presonal param = {}",JSON.toJSON(param));
		List<EntRentUser> oldRentUserList = entRentUserClusterMapper.findComUserList(param);
		logger.info("presonal add record = {}",JSON.toJSON(oldRentUserList));
		Boolean flag = false;
		if(CollectionUtils.isNotEmpty(oldRentUserList)) {
			flag = true;
		}
		logger.info("presonal flag = {}",JSON.toJSON(flag));
		return flag;
	}

	@Override
	public List<ResEntRentUser> findResAll(Map<String, Object> param) {
		return this.entRentUserClusterMapper.findResAll(param);
	}
	
	
}
