package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.linkmore.account.dao.cluster.VehicleMarkManageClusterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.UserGroupInputService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
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
	@Resource
	private EntRentedRecordClient entRentedRecordClient;
	@Resource
	private UserGroupInputService userGroupInputService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
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
						//opsRentEntUserClient.syncRentStallByUserId(user.getId());
						//opsRentEntUserClient.syncRentPersonalUserStallByPlate(bean.getVehMark());
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("userId", user.getId());
						if(bean.getPreId() != null && bean.getPreId().intValue() != 0L) {
							param.put("preId", bean.getPreId());
						}else {
							param.put("preId", 0L);
						}
						param.put("plate", null);
						existFalg = opsRentUserClient.checkExist(param);
						logger.info("------------current plate have the rent privilage---------->>>>>> {}", existFalg);
					}
					userGroupInputService.syncByUserIdAndPlate(user.getId(), bean.getVehMark());
				}
			}
		}else {
			throw new BusinessException(StatusEnum.ACCOUNT_PLATE_LIMIT);
		}
		return existFalg;
	}
	
	@Override
	public int insertByNoRepeat(ReqVehicleMark bean) {
		List<VehicleMarkManage> list = this.findByUserId(bean.getUserId());
		List<String> fieldVlaue = ObjectUtils.findFieldVlaue(list, "vehMark", new String[]{"vehMark"}, new String[] {bean.getVehMark()});

		if(fieldVlaue == null || fieldVlaue.size() <= 0){
			VehicleMarkManage manage = new VehicleMarkManage();
			manage.setVehUserId(String.valueOf(bean.getUserId()));
			manage.setVehMark(bean.getVehMark());
			manage.setCreateTime(new Date());
			manage.setUpdateTime(new Date());
			int num = vehicleMarkManageMasterMapper.insertSelective(manage);
			if(num > 0) {
				syncData(bean.getUserId(), bean.getVehMark());
			}
			/*
			if(num > 0) {
				Map<String,Object> presonParam = new HashMap<String,Object>();
				presonParam.put("plate", bean.getVehMark());
				ReqRentEntUser ent = new ReqRentEntUser();
				ent.setPlate(bean.getVehMark());
				if(opsRentEntUserClient.exists(ent) || opsRentUserClient.exists(presonParam)) {
					opsRentEntUserClient.syncRentStallByUserId(bean.getUserId());
					opsRentEntUserClient.syncRentPersonalUserStallByPlate(bean.getVehMark());
				}
				userGroupInputService.syncByUserIdAndPlate(bean.getUserId(), bean.getVehMark());
			}
			*/
			return num;
		}
		return 0;
	}
	
	@Override
	//@Transactional
	public void deleteById(Long id, HttpServletRequest request) {
		List<ResVechicleMark> list = this.findResList(request);
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		if(user!= null && CollectionUtils.isNotEmpty(list)) {
			for (ResVechicleMark resVechicleMark : list) {
				if(resVechicleMark.getId().equals(id)) {
					ResEntRentedRecord resEntRentedRecord = entRentedRecordClient.findByUserId(user.getId());
					log.info("rent plate flag:{}, using rent record:{}",resVechicleMark.getRentPlateFlag(),JSON.toJSON(resEntRentedRecord));
					if(resVechicleMark.getRentPlateFlag() == true && resEntRentedRecord!= null && resEntRentedRecord.getPlateNo().equals(resVechicleMark.getVehMark())) {
						throw new BusinessException(StatusEnum.DELETE_PLATE_NO_FAILED);
					}
					this.vehicleMarkManageMasterMapper.deleteById(id);
					//userGroupInputService.syncByUserId(user.getId());
					//threadPools.execute(new SyncData(user.getId()));
					syncData(user.getId(),null);
					return;
				}
			}
		}
		throw new RuntimeException("该账户下没有此车牌号");
	}

	
	private BlockingQueue<Runnable> bqueue = new LinkedBlockingQueue<Runnable>(1000);
	private ThreadPoolExecutor threadPools = new ThreadPoolExecutor(2,10, 1, TimeUnit.MINUTES,
			bqueue, new ThreadPoolExecutor.CallerRunsPolicy());
	
	class SyncData implements Runnable {
		private Long userId;
		private String plate;

		public SyncData(Long userId,String plate) {
			this.userId=userId;
			this.plate=plate;
		}
		@Override
		public void run() {
			try {
				//延迟一秒，考虑主从环境，可能会有同步时差
				Thread.sleep(1000);
				/*
				if(userId != null) {
					userGroupInputService.syncByUserId(userId);	//同步用户分组信息
					//opsRentEntUserClient.syncRentStallByUserId(userId); //同步企业长租车位
					//opsRentEntUserClient.syncRentPersonalUserStallByUserId(userId); //同步个人长租车位
				}
				if (StringUtils.isNotEmpty(plate)) {
					//opsRentEntUserClient.syncRentPersonalUserStallByPlate(plate); //同步个人长租车位
				}
				if(userId != null && StringUtils.isNotEmpty(plate)) {
					userGroupInputService.syncByUserIdAndPlate(userId, plate);	//同步用户分组
				}
				*/
				if(userId != null) {
					if(StringUtils.isNotEmpty(plate)) {
						userGroupInputService.syncByUserIdAndPlate(userId, plate);	//同步用户分组
					}else {
						userGroupInputService.syncByUserId(userId);	//同步用户分组信息
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 同步，企业长租车位,个人长租车位，用户分组
	 * @param userId
	 * @param Plate
	 */
	private void syncData(Long userId,String plate) {
		threadPools.execute(new SyncData(userId,plate));
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
		Map<String,Object> param = null;
		List<ResVechicleMark> list = this.vehicleMarkManageClusterMapper.findResList(getCache(request).getId());
		if(CollectionUtils.isNotEmpty(list)) {
			for(ResVechicleMark vm: list) {
				param = new HashMap<String,Object>();
				param.put("userId", vm.getUserId());
				param.put("plate", vm.getVehMark());
				param.put("preId", 0L);
				boolean existFlag = opsRentUserClient.checkExist(param);
				logger.info("------------param:{}---------->>>>>> {}",JSON.toJSON(param), existFlag);
				vm.setRentPlateFlag(existFlag);
			}
		}
		return list;
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
			int count=vehicleMarkManageMasterMapper.updateById(mark);
			//userGroupInputService.syncByUserIdAndPlate(bean.getUserId(), bean.getNewpl());
			syncData(bean.getUserId(), bean.getNewpl());
			return count;
		}
		return 0;
	}
	

}
