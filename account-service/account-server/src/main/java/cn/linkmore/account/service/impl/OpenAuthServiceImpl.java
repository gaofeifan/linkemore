package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;

import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.open.requset.ReqOpenAuth;
import cn.linkmore.account.controller.open.response.ResOpenAuth;
import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.master.UserMasterMapper;
import cn.linkmore.account.entity.Account;
import cn.linkmore.account.entity.OpenSecret;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.OpenAuthService;
import cn.linkmore.account.service.impl.UserServiceImpl.PushThread;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.OpenTokenUtil;
import cn.linkmore.util.TokenUtil;

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

	private UserFactory appUserFactory = AppUserFactory.getInstance();

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResOpenAuth getToken(ReqOpenAuth reqOpenAuth) {

		String secret = String.valueOf(openSecret.getSecrets().get(reqOpenAuth.getAppid()));
		Map<String, Claim> token = null;

		log.info("secret---" + secret);
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
		log.info("key---" + key+"---uid:"+uid+"---mobile"+mobile);
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
		// 放入redis中 返回key
		CacheUser u = new CacheUser();
		u.setId(user.getId());
		u.setAppId(reqOpenAuth.getAppid());
		u.setMobile(user.getMobile());
		u.setToken(key);
		cacheOpenUse(u);
		return new ResOpenAuth(key);
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
		String oldTokenKey = String.valueOf((this.redisService.get("move_token_by_uid" + user.getMobile())));
		this.redisService.remove(appUserFactory.createTokenRedisKey(oldTokenKey, os));

		// 插入缓存
		user.setClient(new Short(os));
		this.redisService.set(appUserFactory.createTokenRedisKey(user.getToken(), os), user, 50 * 60 * 1000);
		this.redisService.set("move_token_by_uid" + user.getMobile(), user.getToken());

	}

}
