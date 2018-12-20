package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.account.dao.cluster.VehicleMarkManageClusterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.UserService;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.prefecture.client.OpsRentEntUserClient;
import cn.linkmore.prefecture.client.OpsRentUserClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class VehicleMarkManageServiceImpl implements VehicleMarkManageService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private RedisService redisService;
	@Resource
	private VehicleMarkManageClusterMapper vehicleMarkManageClusterMapper;
	@Resource
	private VehicleMarkManageMasterMapper vehicleMarkManageMasterMapper;
	@Resource
	private UserService userService;
	@Resource
	private OpsRentEntUserClient opsRentEntUserClient;
	@Resource
	private OpsRentUserClient opsRentUserClient;
	
	@Override
	public List<VehicleMarkManage> findByUserId(Long userId) {
		return this.vehicleMarkManageClusterMapper.findByUserId(userId);
	}

	@Override
	//@Transactional
	public Boolean save(cn.linkmore.account.controller.app.request.ReqVehicleMark bean, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		Boolean existFalg = false;
		ReqVehicleMark mark = ObjectUtils.copyObject(bean,new ReqVehicleMark());
		mark.setUserId(user.getId());
		List<VehicleMarkManage> list = this.findByUserId(user.getId());
		if(list.size() < 3){
			//检查车牌号是否已经存在
			List<String> fieldVlaue = ObjectUtils.findFieldVlaue(list, "vehMark", new String[]{"vehMark"}, new String[] {bean.getVehMark()});
			if(fieldVlaue != null && fieldVlaue.size() > 0){
				throw new BusinessException(StatusEnum.ACCOUNT_PLATE_EXISTS);
			}else{
				VehicleMarkManage manage = new VehicleMarkManage();
				manage.setVehUserId(user.getId().toString());
				manage.setVehMark(bean.getVehMark());
				manage.setCreateTime(new Date());
				manage.setUpdateTime(new Date());
				int num = vehicleMarkManageMasterMapper.insertSelective(manage);
				if(num > 0) {
					Map<String,Object> presonParam = new HashMap<String,Object>();
					presonParam.put("plate", bean.getVehMark());
					ReqRentEntUser ent = new ReqRentEntUser();
					ent.setPlate(bean.getVehMark());
					if(opsRentEntUserClient.exists(ent) || opsRentUserClient.exists(presonParam)) {
						opsRentEntUserClient.syncRentStallByUserId(user.getId());
						opsRentEntUserClient.syncRentPersonalUserStallByPlate(bean.getVehMark());
						if(bean.getPreId() != null && bean.getPreId().intValue() != 0L) {
							Map<String,Object> param = new HashMap<String,Object>();
							param.put("userId", user.getId());
							param.put("preId", bean.getPreId());
							existFalg = opsRentUserClient.checkExist(param);
							logger.info("------------current plate have the rent privilage---------->>>>>> {}", existFalg);
						}
					}
				}
			}
		}else {
			throw new BusinessException(StatusEnum.ACCOUNT_PLATE_LIMIT);
		}
		return existFalg;
	}

	@Override
	@Transactional
	public void deleteById(Long id, HttpServletRequest request) {
		List<ResVechicleMark> list = this.findResList(request);
		for (ResVechicleMark resVechicleMark : list) {
			if(resVechicleMark.getId().equals(id)) {
				this.vehicleMarkManageMasterMapper.deleteById(id);
				return;
			}
		}
		throw new RuntimeException("该账户下没有此车牌号");
	}
	
	

/*
	@Override
	public List<ResVechicleMark> selectResList(HttpServletRequest request) {
		List<ResVechicleMark> resList = this.findResList(getCache(request).getId());
		return resList;
	}
*/
/*	@Override
	public void save(cn.linkmore.account.controller.app.request.ReqVehicleMark bean, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqVehicleMark mark = ObjectUtils.copyObject(bean,new ReqVehicleMark());
		mark.setUserId(user.getId());
		this.save(mark);
	}*/

	/*@Override
	public void deleteById(Long id, HttpServletRequest request) {
		
		ReqVehMarkIdAndUserId v = new ReqVehMarkIdAndUserId();
		v.setUserId(getCache(request).getId());
		v.setVehMarkId(id);
		this.deleteById(v);
	}*/
	
	@Override
	public List<ResVechicleMark> findResList(Long userId) {
		return this.vehicleMarkManageClusterMapper.findResList(userId);
	}

	private CacheUser getCache(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		return (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
	}

	@Override
	public List<ResVechicleMark> findResList(HttpServletRequest request) {
		return this.vehicleMarkManageClusterMapper.findResList(getCache(request).getId());
	}

	@Override
	public ResVechicleMark findById(Long id) {
		return this.vehicleMarkManageClusterMapper.findById(id);
	}

	@Override
	public ResVechicleMark findByPlateNo(Map<String, Object> param) {
		return this.vehicleMarkManageClusterMapper.findByPlateNo(param);
	}

	@Override
	public int update(ReqVehicleMark bean) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", bean.getUserId());
		param.put("plateNo", bean.getVehMark());
		ResVechicleMark vechicle = findByPlateNo(param);
		if(vechicle != null) {
			VehicleMarkManage mark = new VehicleMarkManage();
			mark.setId(vechicle.getId());
			mark.setVehUserId(vechicle.getUserId().toString());
			mark.setUpdateTime(new Date());
			mark.setVehMark(bean.getNewpl());
			return vehicleMarkManageMasterMapper.updateById(mark);
		}
		return 0;
	}
	
}
