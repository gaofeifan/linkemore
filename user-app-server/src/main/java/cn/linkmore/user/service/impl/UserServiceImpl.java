package cn.linkmore.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.constant.PushType;
import cn.linkmore.bean.constant.RedisKey;
import cn.linkmore.bean.constant.SmsTemplate;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqAuthLogin;
import cn.linkmore.user.request.ReqAuthSend;
import cn.linkmore.user.request.ReqMobileBind;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.UserService;
import cn.linkmore.util.JsonUtil;

/**
 * Service实现 - 用户
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SmsClient smsClient; 
	
	@Autowired
	private UserClient userClient; 
	 
	@Autowired
	private PushClient pushClient;
	
	@Autowired
	private AppWechatClient appWechatClient;
	
	@Autowired
	private RedisService redisService;
	
	private final static String TEST_MOBILE= "18612100125|17800242258|13693544138|18810796650|18334787583";

	@Override
	public void send(ReqAuthSend rs) {     
		if(!DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0").equals(rs.getToken())) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		}
		long space = new Date().getTime()-rs.getTimestamp(); 
		if(space>100000||space<-100000) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		}
		if(this.redisService.exists(RedisKey.USER_APP_AUTH_MOBILE+rs.getMobile())) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		} 
		String code = getAppSmsCode(rs.getMobile());
		Map<String,String> param = new HashMap<String,String>();
		param.put("code", code);
		ReqSms sms = new ReqSms();
		sms.setMobile(rs.getMobile());
		sms.setParam(param);
		sms.setSt(SmsTemplate.USER_APP_LOGIN_CODE);
		boolean success = this.smsClient.send(sms);   
		if(success){ 
			this.redisService.set(RedisKey.USER_APP_AUTH_CODE+rs.getMobile(), code, 60*10); 
			this.redisService.set(RedisKey.USER_APP_AUTH_MOBILE+rs.getMobile(), rs.getMobile(),1);
		}else{
			throw new BusinessException(StatusEnum.USER_APP_SMS_FAILED);
		} 
	} 
	private String getAppSmsCode(String mobile){ 
		Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE+mobile);
		String code = null;
		if(cache==null){
			code = String.valueOf(Math.round(Math.random() * 8999 + 1000)); 
		}else{
			code = cache.toString(); 
		} 
		return code;
	}
	@Override
	public ResUser login(ReqAuthLogin rl, HttpServletRequest request) {
		if(!(TEST_MOBILE.contains(rl.getMobile())&&"6666".equals(rl.getCode()))) {
			Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE+rl.getMobile());
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(rl.getCode())) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.USER_APP_AUTH_CODE+rl.getMobile());
				}
			}
		}
		ResUserLogin rul = this.userClient.appLogin(rl.getMobile());
		log.info("userClient.appLogin():{}",JsonUtil.toJson(rul));
		String key = UserCache.getCacheKey(request); 
		ResUser ru = new ResUser();
		ru.setId(rul.getId());
		ru.setMobile(rl.getMobile());
		ru.setToken(key); 
		Token token = this.cacheUser(request, ru);  
		if(token!=null) {
			this.push(ru.getId().toString(), token); 
		}
		return ru;
	}
	
	@Async
	private void push(String uid,Token token) {
		ReqPush rp = new ReqPush();
		rp.setAlias(uid);
		rp.setContent("强制退出,账号已在其它设备登录");
		rp.setData(token.getValue());
		rp.setOs(token.getOs());
		rp.setType(PushType.USER_APP_LOGOUT_NOTICE);
		rp.setTitle("账号已在其它设备登录"); 
		this.pushClient.push(rp);
	}
	
	@Override
	public ResUser login(String code, HttpServletRequest request) {
		ResFans fans = this.appWechatClient.getFans(code);
		ReqUserAppfans ruaf = new ReqUserAppfans();
		ruaf.setCreateTime(fans.getCreateTime());
		ruaf.setHeadurl(fans.getHeadurl());
		ruaf.setId(fans.getId());
		ruaf.setNickname(fans.getNickname());
		ruaf.setRegisterStatus(fans.getRegisterStatus());
		ruaf.setStatus(fans.getStatus());
		ruaf.setUnionid(fans.getUnionid());
		ResUserLogin rul =this.userClient.wxLogin(ruaf);
		String key = UserCache.getCacheKey(request); 
		ResUser ru = new ResUser();
		ru.setId(rul.getId());
		ru.setMobile(rul.getMobile());
		ru.setToken(key); 
		this.cacheUser(request, ru);   
		return ru;
	} 
	
	@Override
	public void bindWechat(String code, HttpServletRequest request) {
		ResFans fans = this.appWechatClient.getFans(code); 
		String key = UserCache.getCacheKey(request);  
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key); 
		ReqUserAppfans ruaf = new ReqUserAppfans();
		ruaf.setCreateTime(fans.getCreateTime());
		ruaf.setHeadurl(fans.getHeadurl());
		ruaf.setId(fans.getId());
		ruaf.setNickname(fans.getNickname());
		ruaf.setRegisterStatus(fans.getRegisterStatus());
		ruaf.setStatus(fans.getStatus());
		ruaf.setUnionid(fans.getUnionid());
		ruaf.setUserId(ru.getId());
		this.userClient.updateAppfans(ruaf);
	}  
	
	private Token cacheUser(HttpServletRequest request, ResUser user) {
		String key = UserCache.getCacheKey(request);
		
		Token last = (Token)this.redisService.get(RedisKey.USER_APP_AUTH_TOKEN+user.getId());
		if(last!=null){ 
			this.redisService.remove(RedisKey.USER_APP_AUTH_TOKEN+user.getId());
			this.redisService.remove(RedisKey.USER_APP_AUTH_USER+last.getValue());  
			last.setValue(key);
		}
		this.redisService.set(RedisKey.USER_APP_AUTH_USER+key, user); 
		Token token = new Token();
		token.setOs(new Short(request.getHeader("os")==null?"0":request.getHeader("os")));
		token.setTimestamp(new Date().getTime());
		token.setValue(key);
		this.redisService.set(RedisKey.USER_APP_AUTH_TOKEN+user.getId(), token); 
		return last;
	}
	/**
	 * 更新缓存中的用户信息
	 * @param request 用户请求
	 * @param user 用户信息
	 */
	private void updateCache(HttpServletRequest request, ResUser user){
		String key = UserCache.getCacheKey(request);
		this.redisService.set(RedisKey.USER_APP_AUTH_USER+key, user); 
	}
	@Override
	public void bindMobile(ReqMobileBind rmb, HttpServletRequest request) {
		Object cache = this.redisService.get(RedisKey.USER_APP_USER_CODE+rmb.getMobile());
		if(cache==null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
		}else {
			if(!cache.toString().equals(rmb.getCode())) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
			}else {
				this.redisService.remove(RedisKey.USER_APP_USER_CODE+rmb.getMobile());
			}
		} 
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key); 
		ReqUpdateMobile rum = new ReqUpdateMobile(); 
		rum.setMobile(rmb.getMobile());
		rum.setUserId(ru.getId());
		this.userClient.updateMobile(rum);
		this.updateCache(request, ru); 
	}
	@Override
	public void send(String mobile, HttpServletRequest request) {   
		if(this.redisService.exists(RedisKey.USER_APP_USER_MOBILE+mobile)) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		} 
		String code = getAppSmsCode(mobile);
		Map<String,String> param = new HashMap<String,String>();
		param.put("code", code);
		ReqSms sms = new ReqSms();
		sms.setMobile(mobile);
		sms.setParam(param);
		sms.setSt(SmsTemplate.USER_APP_LOGIN_CODE);
		boolean success = this.smsClient.send(sms);   
		if(success){ 
			this.redisService.set(RedisKey.USER_APP_USER_CODE+mobile, code, 60*10); 
			this.redisService.set(RedisKey.USER_APP_USER_MOBILE+mobile, mobile,1);
		}else{
			throw new BusinessException(StatusEnum.USER_APP_SMS_FAILED);
		} 
	}
	@Override
	public void updateNickname(ReqUpdateNickname nickname) {
		this.userClient.updateNickname(nickname);
	}
	@Override
	public void updateSex(ReqUpdateSex sex) {
		this.userClient.updateSex(sex);
	}
	@Override
	public void updateVehicle(ReqUpdateVehicle req) {
		this.userClient.updateVehicle(req);
	}
	@Override
	public ResUserDetails detail(Long userId) {
		return this.detail(userId);
	}
	@Override
	public void removeWechat(Long userId) {
		this.userClient.removeWechat(userId);
	}
	
	@Override
	public cn.linkmore.account.response.ResUser selectByMobile(String mobile) {
		return this.userClient.selectByMobile(mobile);
	}
	
	
	

}
