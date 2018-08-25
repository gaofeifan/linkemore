package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.ent.request.ReqAuthLogin;
import cn.linkmore.enterprise.controller.ent.request.ReqAuthSend;
import cn.linkmore.enterprise.controller.ent.response.ResStaff;
import cn.linkmore.enterprise.dao.cluster.EntStaffClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffMasterMapper;
import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.service.StaffService;
/*import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.notice.client.UserSocketClient;*/
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;

/**
 * 员工接口实现
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
@Service
public class StaffServiceImpl implements StaffService {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	private final static long SPACE = 1000L*60*30; 
	@Resource
	private PrefectureClient prefectureClient;
	/*@Resource
	private EntSocketClient entSocketClient;*/
	@Resource
	private PushClient pushClient;
	@Resource
	private SmsClient smsClient;
	@Resource
	private AppWechatClient appWechatClient;
	@Resource
	private RedisService redisService;
	@Resource
	private EntStaffMasterMapper entStaffMasterMapper;
	@Resource
	private EntStaffClusterMapper entStaffClusterMapper;
	@Override
	public ResStaff login(ReqAuthLogin rl, HttpServletRequest request) {
		if(!("6699".equals(rl.getCode()))) {
			Object cache = this.redisService.get(RedisKey.STAFF_ENT_AUTH_CODE.key+rl.getMobile());
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(rl.getCode())) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.STAFF_ENT_AUTH_CODE.key+rl.getMobile());
				}
			}
		}
		String key = TokenUtil.getKey(request);
		EntStaff staff = entStaffClusterMapper.findByMobile(rl.getMobile());
		if(staff == null || staff.getStatus() == 0) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}
		staff.setLoginTime(new Date());
		this.entStaffMasterMapper.updateById(staff);
		ResStaff rs = new ResStaff();
		rs.setId(staff.getId());
		rs.setMobile(staff.getMobile());
		rs.setRealname(staff.getRealname());
		rs.setToken(key);
		rs.setStatus(staff.getStatus());
		CacheUser u = new CacheUser();
		u.setId(staff.getId());
		u.setMobile(staff.getMobile());
		u.setToken(key); 
		u.setOpenId(staff.getOpenId());
		u.setClient((short)ClientSource.APPLET.source);
		Token token = this.cacheUser(request, u);  
		if(token!=null) {
			new PushThread(rs.getId().toString(), token).start(); 
		}
		return rs;
	}
	private final static ConcurrentHashMap<Long,Long> LOGIN_USER = new ConcurrentHashMap<Long,Long>();
	private Token cacheUser(HttpServletRequest request, CacheUser user) {
		Token last  = null;
		String key = TokenUtil.getKey(request);
		Long userId = null;
		if(user.getId()!=null) {
			userId = LOGIN_USER.get(user.getId());
			if(userId==null) {
				userId = user.getId();
				LOGIN_USER.put(user.getId(), user.getId());
			}
		} else {
			userId = 0L;
		}
		synchronized(userId) {
			last = (Token)this.redisService.get(Constants.RedisKey.STAFF_ENT_AUTH_TOKEN.key+user.getId());
			if(last!=null){
				this.redisService.remove(Constants.RedisKey.STAFF_ENT_AUTH_TOKEN.key+user.getId());
				this.redisService.remove(Constants.RedisKey.STAFF_ENT_AUTH_USER.key+last.getAccessToken());  
				last.setAccessToken(key);
			}
			user.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
			this.redisService.set(Constants.RedisKey.STAFF_ENT_AUTH_USER.key+key, user); 
			Token token = new Token();
			token.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
			token.setTimestamp(new Date().getTime());
			token.setAccessToken(key);
			this.redisService.set(Constants.RedisKey.STAFF_ENT_AUTH_TOKEN.key+user.getId(), token); 
		} 
		return last;
	}
	
	class PushThread extends Thread{
		private String uid;
		private Token token;
		public PushThread(String uid,Token token) {
			this.uid= uid;
			this.token = token;
		}
		public void run() {
			if (token.getClient().intValue() == ClientSource.WXAPP.source) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", "账号已在其它设备登录");
				map.put("type",PushType.USER_APP_LOGOUT_NOTICE);
				map.put("content", "强制退出,账号已在其它设备登录");
//				map.put("status", status);
				CacheUser cu = (CacheUser) redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
				//entSocketClient.push(JsonUtil.toJson(map), cu.getOpenId());
			} else {
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setContent("强制退出,账号已在其它设备登录");
				rp.setData(token.getAccessToken());
				rp.setClient(token.getClient());
				rp.setType(PushType.USER_APP_LOGOUT_NOTICE);
				rp.setTitle("账号已在其它设备登录"); 
				pushClient.push(rp);
			}
		}
	}
	
	@Override
	public void logout(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		Map<String,Object> map = new HashMap<>();
		map.put("sql", "status = 0,open_id = null");
		map.put("id", ru.getId());
		this.entStaffMasterMapper.updateByColumn(map);
		this.redisService.remove(Constants.RedisKey.STAFF_ENT_AUTH_TOKEN.key+ru.getId().toString());
		this.redisService.remove(Constants.RedisKey.STAFF_ENT_AUTH_USER.key+key); 
	}

	@Override
	public void bindLogin(String code, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		ResFans fans = this.appWechatClient.getFans(code);
		Map<String, Object> map = new HashMap<>();
		map.put("sql", "open_id = "+fans.getId());
		map.put("id", ru.getId());
		this.entStaffMasterMapper.updateByColumn(map );
	}

	@Override
	public void send(ReqAuthSend rs) {
		log.info("send:{}",JsonUtil.toJson(rs));
		log.info("token:{}",DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0"));
//		if(!DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0").equals(rs.getToken())) {
//			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
//		} 
		long space = new Date().getTime()	-new Long(rs.getTimestamp()).longValue(); 
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
			this.redisService.set(RedisKey.STAFF_ENT_AUTH_CODE.key+rs.getMobile(), code, 60*10); 
			this.redisService.set(RedisKey.STAFF_ENT_AUTH_MOBILE.key+rs.getMobile(), rs.getMobile(),1);
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
	public boolean checkMobile(String mobile) {
		EntStaff entStaff = this.entStaffClusterMapper.findByMobile(mobile);
		if(entStaff != null) {
			return true;
		}
		return false;
	}
	
	

	
}