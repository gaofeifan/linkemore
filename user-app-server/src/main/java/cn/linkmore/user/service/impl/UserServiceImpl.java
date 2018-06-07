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
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.SmsTemplate;
import cn.linkmore.bean.common.security.Token;
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
import cn.linkmore.user.request.ReqUpdateVehicle;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.UserService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

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
	
	private final static long SPACE = 1000L*60*30;
	
	private final static String TEST_MOBILE= "18511509492|18801243820|18010161135|18510770300|18612100125|17800242258|13693544138|18810796650|18334787583|18514410536";

	@Override
	public void send(ReqAuthSend rs) {     
		log.info("send:{}",JsonUtil.toJson(rs));
		log.info("token:{}",DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0"));
		if(!DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0").equals(rs.getToken())) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		} 
		long space = new Date().getTime()-new Long(rs.getTimestamp()).longValue(); 
		log.info("space:{},SPACE:{} verify:{}",space,SPACE,space>SPACE||space<-SPACE);
		if(space>SPACE||space<-SPACE) {
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
		sms.setSt(Constants.SmsTemplate.USER_APP_LOGIN_CODE);
		boolean success = this.smsClient.send(sms);   
		if(success){ 
			this.redisService.set(RedisKey.USER_APP_AUTH_CODE.key+rs.getMobile(), code, 60*10); 
			this.redisService.set(RedisKey.USER_APP_AUTH_MOBILE.key+rs.getMobile(), rs.getMobile(),1);
		}else{
			throw new BusinessException(StatusEnum.USER_APP_SMS_FAILED);
		} 
	} 
	private String getAppSmsCode(String mobile){ 
		Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE.key+mobile);
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
			Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE.key+rl.getMobile());
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(rl.getCode())) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.USER_APP_AUTH_CODE.key+rl.getMobile());
				}
			}
		}
		ResUserLogin rul = this.userClient.appLogin(rl.getMobile());
		if(rul==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		} 
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
		rp.setData(token.getAccessToken());
		rp.setClient(token.getClient());
		rp.setType(PushType.USER_APP_LOGOUT_NOTICE);
		rp.setTitle("账号已在其它设备登录"); 
		this.pushClient.push(rp);
	}
	
	@Override
	public ResUser login(String code, HttpServletRequest request) {
		ResFans fans = this.appWechatClient.getFans(code);
		if(fans==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_LOGIN_ERROR);
		} 
		ReqUserAppfans ruaf = new ReqUserAppfans();
		ruaf.setCreateTime(fans.getCreateTime());
		ruaf.setHeadurl(fans.getHeadurl());
		ruaf.setId(fans.getId());
		ruaf.setNickname(fans.getNickname());
		ruaf.setRegisterStatus(fans.getRegisterStatus());
		ruaf.setStatus(fans.getStatus());
		ruaf.setUnionid(fans.getUnionid());
		ResUserLogin rul =this.userClient.wxLogin(ruaf);
		if(rul==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		} 
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
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
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
		
		Token last = (Token)this.redisService.get(Constants.RedisKey.USER_APP_AUTH_TOKEN.key+user.getId().toString());
		if(last!=null){ 
			this.redisService.remove(Constants.RedisKey.USER_APP_AUTH_TOKEN.key+user.getId().toString());
			this.redisService.remove(Constants.RedisKey.USER_APP_AUTH_USER.key+last.getAccessToken());  
			last.setAccessToken(key);
		}
		this.redisService.set(Constants.RedisKey.USER_APP_AUTH_USER.key+key, user); 
		Token token = new Token();
		token.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
		token.setTimestamp(new Date().getTime());
		token.setAccessToken(key);
		this.redisService.set(Constants.RedisKey.USER_APP_AUTH_TOKEN.key+user.getId(), token); 
		return last;
	}
	/**
	 * 更新缓存中的用户信息
	 * @param request 用户请求
	 * @param user 用户信息
	 */
	private void updateCache(HttpServletRequest request, ResUser user){
		String key = UserCache.getCacheKey(request);
		this.redisService.set(RedisKey.USER_APP_AUTH_USER.key+key, user); 
	}
	@Override
	public void bindMobile(ReqMobileBind rmb, HttpServletRequest request) {
		Object cache = this.redisService.get(RedisKey.USER_APP_USER_CODE.key+rmb.getMobile());
		if(cache==null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
		}else {
			if(!cache.toString().equals(rmb.getCode())) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
			}else {
				this.redisService.remove(RedisKey.USER_APP_USER_CODE.key+rmb.getMobile());
			}
		} 
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateMobile rum = new ReqUpdateMobile(); 
		rum.setMobile(rmb.getMobile());
		rum.setUserId(ru.getId());
		this.userClient.updateMobile(rum);
		this.updateCache(request, ru); 
	}
	@Override
	public void send(String mobile, HttpServletRequest request) {   
		if(this.redisService.exists(RedisKey.USER_APP_USER_MOBILE.key+mobile)) {
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
			this.redisService.set(RedisKey.USER_APP_USER_CODE.key+mobile, code, 60*10); 
			this.redisService.set(RedisKey.USER_APP_USER_MOBILE.key+mobile, mobile,1);
		}else{
			throw new BusinessException(StatusEnum.USER_APP_SMS_FAILED);
		} 
	}
	@Override
	public void updateNickname(String nickname, HttpServletRequest request) {
		ResUser cache = getCache(request);
		ReqUpdateNickname nick = new ReqUpdateNickname();
		nick.setNickname(nickname);
		nick.setUserId(cache.getId());
		this.userClient.updateNickname(nick);
	}
	@Override
	public void updateSex(Integer sex, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateSex req = new ReqUpdateSex();
		req.setSex(sex);
		req.setUserId(ru.getId());
		this.userClient.updateSex(req);
	}
	@Override
	public void updateVehicle(ReqUpdateVehicle vehicle, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		vehicle.setUserId(ru.getId());
		cn.linkmore.account.request.ReqUpdateVehicle object = ObjectUtils.copyObject(vehicle, new cn.linkmore.account.request.ReqUpdateVehicle());
		this.userClient.updateVehicle(object);
	}
	@Override
	public ResUserDetails detail(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		return this.userClient.detail(ru.getId());
	}
	
	@Override
	public void removeWechat(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		this.userClient.removeWechat(ru.getId());
	}
	
	@Override
	public ResUser getCache(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		return (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
	}
	
}
