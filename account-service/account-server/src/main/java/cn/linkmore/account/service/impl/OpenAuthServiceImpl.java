package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import cn.linkmore.account.controller.open.requset.ReqOpenAuth;
import cn.linkmore.account.controller.open.requset.ReqOpenUser;
import cn.linkmore.account.controller.open.response.ResOpenAuth;
import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.cluster.VehicleMarkManageClusterMapper;
import cn.linkmore.account.dao.master.UserMasterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.OpenSecret;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.service.OpenAuthService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.OpenTokenUtil;

@Service
public class OpenAuthServiceImpl implements OpenAuthService {

	@Autowired
	private OpenSecret openSecret;
	@Resource
	private RedisService redisService;
	@Resource
	private UserClusterMapper userClusterMapper;
	@Resource
	private UserMasterMapper userMasterMapper;

	@Resource
	private VehicleMarkManageClusterMapper vehicleMarkManageClusterMapper;

	@Resource
	private VehicleMarkManageMasterMapper vehicleMarkManageMasterMapper;

	private UserFactory appUserFactory = AppUserFactory.getInstance();

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResOpenAuth getToken(ReqOpenAuth reqOpenAuth) {
		String secret = String.valueOf(openSecret.getSecrets().get(reqOpenAuth.getAppid()));
		Map<String, Claim> token = null;
		log.info("request param = {}, secret = {}", JSON.toJSON(reqOpenAuth), secret);
		if (secret == null) {
			throw new BusinessException(StatusEnum.OPENAPI_APPID_ERROR);
		}
		try {
			token = OpenTokenUtil.verifyToken(reqOpenAuth.getToken(), secret);
		} catch (Exception e) {
			throw new BusinessException(StatusEnum.OPENAPI_SIGN_ERROR);
		}
		String key = UUID.randomUUID().toString().replaceAll("-", "");
		String uid = token.get("uid").asString();
		String mobile = token.get("mobile").asString();
		String plates = token.get("plates").asString();
		log.info("response result uid = {}, mobile = {} , plates = {}", uid, mobile, plates);
		// 查询登陆用户
		ResUser user = this.userClusterMapper.findByMobile(uid);
		if (user == null) {
			user = new ResUser();
			user.setMobile(mobile);
			user.setUsername(uid);
			user.setPassword("");
			user.setUserType("3");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 2);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short) 0);
			this.userMasterMapper.save(user);
		} else {
			user.setLastLoginTime(new Date());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
			this.updateFansStatus((short) 0, user.getId());
		}

		// 解析用户传递参数，若包含车牌，则初始化凌猫系统中
		if (plates.startsWith("[")) {
			plates = plates.substring(1);
		}
		if (plates.endsWith("]")) {
			plates = plates.substring(0, plates.length() - 1);
		}

		syncUserPlate(plates, user.getId());

		// 放入redis中 返回key
		CacheUser u = new CacheUser();
		u.setId(user.getId());
		u.setAppId(reqOpenAuth.getAppid());
		u.setMobile(user.getMobile());
		u.setToken(key);
		cacheOpenUse(u);
		return new ResOpenAuth(key);
	}

	@Override
	public String getToken(ReqOpenUser reqOpenUser) {
		//ResUser user = this.userClusterMapper.findByMobile(reqOpenUser.getPhone());
		ResUser user = this.userClusterMapper.findByMobile(reqOpenUser.getAccountName());
		if (user == null) {
			user = new ResUser();
			user.setMobile(reqOpenUser.getPhone());
			user.setUsername(reqOpenUser.getAccountName());
			user.setNickname(reqOpenUser.getNickName());
			user.setPassword("");
			user.setUserType("4");
			user.setStatus("1");
			user.setSex(reqOpenUser.getSex());
			user.setIcon(reqOpenUser.getIcon());
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 2);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short) 0);
			this.userMasterMapper.save(user);
		} else {
			user.setLastLoginTime(new Date());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
			this.updateFansStatus((short) 0, user.getId());
		}
		// 放入redis中 返回key
		String key = UUID.randomUUID().toString().replaceAll("-", "");
		
		CacheUser u = new CacheUser();
		u.setId(user.getId());
		u.setAppId("appid");
		u.setMobile(user.getMobile());
		u.setToken(key);
		cacheOpenUse2(u);
		

		return key;
	}
	
	
	private void syncUserPlate(String plates, Long userId) {
		List<VehicleMarkManage> plateList = this.vehicleMarkManageClusterMapper.findByUserId(userId);
		List<VehicleMarkManage> initPlateList = new ArrayList<VehicleMarkManage>();
		VehicleMarkManage vmark = null;
		log.info("syncUserPlate plates:{}, userId:{}, ownPlateList:{}", plates, userId, JSON.toJSON(plateList));
		// 新记录增加
		if (plates.length() > 0) {
			String[] plateArr = plates.split(",");
			for (String plate : plateArr) {
				plate = plate.trim();
				vmark = new VehicleMarkManage();
				vmark.setVehMark(plate);
				initPlateList.add(vmark);
				if (!existPlate(plateList, plate)) {
					log.info("insert the new plate {}", plate);
					// 已有车牌列表中不存在该车牌记录，添加车牌
					VehicleMarkManage manage = new VehicleMarkManage();
					manage.setVehUserId(userId.toString());
					manage.setVehMark(plate);
					manage.setCreateTime(new Date());
					manage.setUpdateTime(new Date());
					int num = vehicleMarkManageMasterMapper.insertSelective(manage);
				}
			}
		}
		// 变更记录删除
		List<Long> ids = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(plateList)) {
			for (VehicleMarkManage vm : plateList) {
				if (!existPlate(initPlateList, vm.getVehMark())) {
					ids.add(vm.getId());
				}
			}
		}

		if (CollectionUtils.isNotEmpty(ids)) {
			log.info("delete the old plate list size={},data={}", ids.size(), JSON.toJSON(ids));
			for (Long id : ids) {
				vehicleMarkManageMasterMapper.deleteById(id);
			}
		}
	}

	private boolean existPlate(List<VehicleMarkManage> ownerPlateList, String plate) {
		if (CollectionUtils.isNotEmpty(ownerPlateList)) {
			for (VehicleMarkManage vm : ownerPlateList) {
				if (vm.getVehMark().equals(plate)) {
					return true;
				}
			}
		}
		return false;
	}

	private void updateFansStatus(Short fansStaus, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("updateTime", new Date());
		param.put("fansStatus", fansStaus);
		this.userMasterMapper.updateFansStatus(param);
	}

	void cacheOpenUse(CacheUser user) {
		String os = "4";
		user.setClient(new Short(os));

		// 删除旧缓存
		String oldTokenKey = String.valueOf((this.redisService.get(RedisKey.USER_APP_AUTH_OPEN.key + user.getMobile())));
		this.redisService.remove(appUserFactory.createTokenRedisKey(oldTokenKey, os));

		// 插入缓存
		user.setClient(new Short(os));
		this.redisService.set(appUserFactory.createTokenRedisKey(user.getToken(), os), user, 60 * 60 * 24 * 2);
		this.redisService.set(RedisKey.USER_APP_AUTH_OPEN.key + user.getMobile(), user.getToken());

	}
	
	void cacheOpenUse2(CacheUser user) {
		String os = "3";
		user.setClient(new Short(os));

		// 删除旧缓存
		String oldTokenKey = String.valueOf((this.redisService.get(RedisKey.USER_APP_AUTH_OPEN.key + user.getMobile())));
		this.redisService.remove(appUserFactory.createTokenRedisKey(oldTokenKey, os));

		// 插入缓存
		user.setClient(new Short(os));
		this.redisService.set(appUserFactory.createTokenRedisKey(user.getToken(), os), user, 60 * 60 * 24 * 2);
		this.redisService.set(RedisKey.USER_APP_AUTH_OPEN.key + user.getMobile(), user.getToken());

	}


	@Override
	public ResOpenAuth getAccessToken(ReqOpenAuth reqOpenAuth) {
		String secret = String.valueOf(openSecret.getSecrets().get(reqOpenAuth.getAppid()));
		Map<String, Claim> token = null;
		log.info("request param = {}, secret = {}", JSON.toJSON(reqOpenAuth), secret);
		if (secret == null) {
			throw new BusinessException(StatusEnum.OPENAPI_APPID_ERROR);
		}
		try {
			token = OpenTokenUtil.verifyToken(reqOpenAuth.getToken(), secret);
		} catch (Exception e) {
			throw new BusinessException(StatusEnum.OPENAPI_SIGN_ERROR);
		}
		String key = UUID.randomUUID().toString().replaceAll("-", "");
		String uid = token.get("uid").asString();
		String mobile = token.get("mobile").asString();
		//String plates = token.get("plates").asString();
		log.info("response result uid = {}, mobile = {} ", uid, mobile);
		// 查询登陆用户
		ResUser user = this.userClusterMapper.findByMobile(uid);
		if (user == null) {
			user = new ResUser();
			user.setMobile(mobile);
			user.setUsername(uid);
			user.setPassword("");
			user.setUserType("3");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 2);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short) 0);
			this.userMasterMapper.save(user);
		} else {
			user.setLastLoginTime(new Date());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
			this.updateFansStatus((short) 0, user.getId());
		}

		// 解析用户传递参数，若包含车牌，则初始化凌猫系统中
		/*if (plates.startsWith("[")) {
			plates = plates.substring(1);
		}
		if (plates.endsWith("]")) {
			plates = plates.substring(0, plates.length() - 1);
		}

		syncUserPlate(plates, user.getId());*/

		// 放入redis中 返回key
		CacheUser u = new CacheUser();
		u.setId(user.getId());
		u.setAppId(reqOpenAuth.getAppid());
		u.setMobile(user.getMobile());
		u.setToken(key);
		cacheOpenUse(u);
		return new ResOpenAuth(key);
	}

}
