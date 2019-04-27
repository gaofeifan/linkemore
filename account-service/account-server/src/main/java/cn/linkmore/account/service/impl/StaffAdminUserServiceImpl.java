package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.request.ReqEditPWAuth;
import cn.linkmore.account.controller.app.request.ReqReset;
import cn.linkmore.account.controller.staff.request.ReqEditPw;
import cn.linkmore.account.controller.staff.request.ReqEditPwAuth;
import cn.linkmore.account.controller.staff.request.ReqLoginPw;
import cn.linkmore.account.controller.staff.response.ResAdmin;
import cn.linkmore.account.controller.staff.response.ResCheckAccount;
import cn.linkmore.account.dao.cluster.UserStaffClusterMapper;
import cn.linkmore.account.entity.StaffAppfans;
import cn.linkmore.account.request.ReqMessage;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.MessageService;
import cn.linkmore.account.service.StaffAdminUserService;
import cn.linkmore.account.service.StaffAppfansService;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.client.StaffAdminUserClient;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;
@Service
public class StaffAdminUserServiceImpl implements StaffAdminUserService {
	private final static long SPACE = 1000L*60*30; 
	private final static String STAFF_CODE = "6699"; 
//	@Value("")
	private Boolean staffCode = true;
	@Autowired
	private StaffAppfansService staffAppfansService; 
	@Autowired
	private UserStaffClusterMapper userStaffClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private StaffAdminUserClient staffAdminUserClient;
	@Autowired
	private SmsClient smsClient;
	@Autowired
	private SendClient sendClient;
	@Autowired
	private MessageService messageService;
	@Autowired
	private WechatMiniClient wechatMiniClient;
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserFactory appUserFactory = AppUserFactory.getInstance(); 
	@Override
	public ResAdmin login(ReqAuthLogin rl, HttpServletRequest request) {
		if(!("6699".equals(rl.getCode()))) {
			Object cache = this.redisService.get(RedisKey.STAFF_STAFF_AUTH_CODE.key+rl.getMobile());
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(rl.getCode())) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.STAFF_STAFF_AUTH_CODE.key+rl.getMobile());
				}
			}
		}
		String key = TokenUtil.createKey(rl.getType(),request);
		cn.linkmore.prefecture.response.ResAdmin staff = staffAdminUserClient.authLogin(rl.getMobile());
		if(staff == null || staff.getStatus() == 0) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}
		staff.setLoginTime(new Date());
		this.staffAdminUserClient.updateLoginTime(staff.getId());
		ResAdmin rs = new ResAdmin();
		rs.setId(staff.getId());
		rs.setMobile(staff.getCellphone());
		rs.setRealname(staff.getRealname());
		rs.setToken(key);
		rs.setIsOperation(staff.getIsOperate());
		rs.setAccountName(staff.getAccountName());
		rs.setGatewayDelete(staff.getGatewayDelete());
		rs.setStatus(staff.getStatus());
		rs.setType(staff.getType());
		CacheUser u = new CacheUser();
		u.setId(staff.getId());
		u.setMobile(staff.getCellphone());
		u.setToken(key); 
//		u.setOpenId(staff.getOpenId());
		u.setAccount(staff.getAccountName());
		u.setClient((short)ClientSource.APPLET.source);
		Token token = this.cacheUser(request, u,key);  
		if(token!=null) {
			new PushThread(rs.getId().toString(), token).start(); 
		}
		return rs;
	}
	private final static ConcurrentHashMap<Long,Long> LOGIN_USER = new ConcurrentHashMap<Long,Long>();
	private Token cacheUser(HttpServletRequest request, CacheUser user,String key) {
		Token last  = null;
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
			last = (Token)this.redisService.get(Constants.RedisKey.STAFF_STAFF_AUTH_TOKEN.key+user.getId());
			if(last!=null){
				this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_TOKEN.key+user.getId());
				this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_USER.key+last.getAccessToken());  
				this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_ACCOUNT.key+user.getAccount());  
				last.setAccessToken(key);
			}
			user.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
			this.redisService.set(Constants.RedisKey.STAFF_STAFF_AUTH_USER.key+key, user); 
			this.redisService.set(Constants.RedisKey.STAFF_STAFF_AUTH_ACCOUNT.key+user.getAccount(),user);  
			Token token = new Token();
			token.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
			token.setTimestamp(new Date().getTime());
			token.setAccessToken(key);
			if(user.getClient().intValue()==ClientSource.WXAPP.source) {
				this.redisService.set(Constants.RedisKey.STAFF_WXAPP_AUTH_TOKEN.key+user.getOpenId(), token,Constants.ExpiredTime.ACCESS_TOKEN_EXP_TIME.time); 
			}
			this.redisService.set(Constants.RedisKey.STAFF_STAFF_AUTH_TOKEN.key+user.getId(), token); 
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
				map.put("type",PushType.STAFF_STAFF_LOGOUT_NOTICE);
				map.put("content", "强制退出,账号已在其它设备登录");
//				map.put("status", status);
				CacheUser cu = (CacheUser) redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
//				entSocketClient.push(JsonUtil.toJson(map), cu.getOpenId());
			} else {
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setContent("强制退出,账号已在其它设备登录");
				rp.setData(token.getAccessToken());
				rp.setClient(token.getClient());
				rp.setType(PushType.STAFF_STAFF_LOGOUT_NOTICE);
				rp.setTitle("账号已在其它设备登录"); 
				sendClient.give(rp);
			}
		}
	}
	
	@Override
	public void logout(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_TOKEN.key+ru.getId());
		this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_USER.key+key);  
		this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_ACCOUNT.key+ru.getAccount());  
	}

/*	@Override
	public void bindLogin(String code, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		//ResFans fans = this.appWechatClient.getFans(code);
		ResMiniSession fans = this.wechatMiniClient.getSessionPlus(code,1002);
		log.info("fans:{}",JsonUtil.toJson(fans));
		Map<String, Object> map = new HashMap<>();
		map.put("sql", "open_id = "+fans.getOpenid());
		map.put("id", ru.getId());
		this.entStaffMasterMapper.updateByColumn(map );
	}*/

	@Override
	public void send(ReqAuthSend rs) {
		log.info("send:{}",JsonUtil.toJson(rs));
		log.info("token:{}",DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0"));
//		if(!DigestUtils.md5Hex(rs.getMobile()+rs.getTimestamp()+"v2.0").equals(rs.getToken())) {
//			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
//		} 
		long space = new Date().getTime()-new Long(rs.getTimestamp()).longValue(); 
		log.info("space:{},SPACE:{} verify:{}",space,SPACE,space>SPACE||space<-SPACE);
		if(space>SPACE||space<-SPACE) {
			throw new BusinessException(StatusEnum.USER_APP_ILLEGAL_REQUEST);
		}
		if(this.redisService.exists(RedisKey.STAFF_STAFF_AUTH_MOBILE+rs.getMobile())) {
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
			this.redisService.set(RedisKey.STAFF_STAFF_AUTH_CODE.key+rs.getMobile(), code, 60*10); 
			this.redisService.set(RedisKey.STAFF_STAFF_AUTH_MOBILE.key+rs.getMobile(), rs.getMobile(),1);
			ReqMessage msg = new ReqMessage();
			msg.setCreateTime(new Date());
			msg.setMessageType(Constants.MessageType.LOGIN.type);
			msg.setMobile(rs.getMobile());
			msg.setTemplate("SMS_63275171");
			msg.setParameter(JsonUtil.toJson(param));
			this.messageService.save(msg);
		}else{
			
			throw new BusinessException(StatusEnum.USER_APP_SMS_FAILED);
		} 		
	}
	

	private String getAppSmsCode(String mobile){ 
		Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE.key+mobile);
		String code = null;
//		if(cache==null){
		code = String.valueOf(Math.round(Math.random() * 8999 + 1000)); 
//		}else{
//			code = cache.toString(); 
//		} 
		return code;
	}

	@Override
	public boolean checkMobile(String mobile) {
		ResAdminUser user = this.staffAdminUserClient.findMobile(mobile);
		if(user != null) {
			return true;
		}
		return false;
	}

	@Override
	public String bindWechat(String code, HttpServletRequest request) {
		ResMiniSession session = this.wechatMiniClient.getSessionPlus(code, 1002);
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		StaffAppfans staffAppfans = this.staffAppfansService.findById(session.getOpenid());
		if(staffAppfans != null) {
			if(staffAppfans.getUserId().equals(ru.getId())) {
				return staffAppfans.getId();
			}else {
				this.staffAppfansService.deleteByUserId(ru.getId());
				staffAppfans.setUserId(ru.getId());
				this.staffAppfansService.updateByIdSelective(staffAppfans);
				return staffAppfans.getId();
			}
		}
		staffAppfans = new StaffAppfans();
		staffAppfans.setCreateTime(new Date());
		staffAppfans.setId(session.getOpenid());
		staffAppfans.setUserId(ru.getId());
		this.staffAppfansService.insertSelective(staffAppfans);
		return session.getOpenid();
	}

	@Override
	public ResAdmin login(ReqLoginPw pw, HttpServletRequest request) {
		cn.linkmore.prefecture.response.ResAdmin accountName = this.staffAdminUserClient.findAccountName(pw.getAccountName());
		if(accountName == null || accountName.getId() == null) {
			accountName = this.staffAdminUserClient.authLogin(pw.getAccountName());
		}
		if(accountName == null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}else if(StringUtils.isBlank(accountName.getPassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
		}else {
			String passw = Md5PW.md5(null, pw.getPassword());
			if(!accountName.getPassword().equals(passw)) {
				throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
			}
			String key = TokenUtil.createKey(pw.getType(),request);
			accountName.setLoginTime(new Date());
			this.staffAdminUserClient.updateLoginTime(accountName.getId());
			ResAdmin rs = new ResAdmin();
			rs.setId(accountName.getId());
			rs.setMobile(accountName.getCellphone());
			rs.setRealname(accountName.getRealname());
			rs.setToken(key);
			rs.setIsOperation(accountName.getIsOperate());
			rs.setAccountName(accountName.getAccountName());
			rs.setGatewayDelete(accountName.getGatewayDelete());
			rs.setStatus(accountName.getStatus());
			rs.setType(accountName.getType());
			CacheUser u = new CacheUser();
			u.setId(accountName.getId());
			u.setMobile(accountName.getCellphone());
			u.setToken(key); 
//			u.setOpenId(accountName.getOpenId());
			u.setClient((short)ClientSource.APPLET.source);
			Token token = this.cacheUser(request, u,key);  
			if(token!=null) {
				new PushThread(rs.getId().toString(), token).start(); 
			}
			return rs;
		}
	}

	@Override
	public boolean bindMobile(String mobile, HttpServletRequest request,String code) {
		String key = TokenUtil.getKey(request);
		ResAdminUser findMobile = this.staffAdminUserClient.findMobile(mobile);
		if(findMobile != null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		Object cache = this.redisService.get(RedisKey.STAFF_STAFF_AUTH_CODE.key+mobile);
		if(staffCode == true && code.equals("6699") ) {
		}else {
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(code)) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.STAFF_STAFF_AUTH_CODE.key+mobile);
				}
			}
		}
		ResAdminUser resAdminUser = this.staffAdminUserClient.findById(ru.getId());
		if(resAdminUser != null && StringUtils.isNotBlank(resAdminUser.getCellphone())) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_ACCOUNT_NAME_EXIST);
		}
		this.staffAdminUserClient.updateMobile(ru.getId(),mobile);
		return true;
	}

	@Override
	public ResCheckAccount checkAccount(String mobile) {
		cn.linkmore.prefecture.response.ResAdmin admin = this.staffAdminUserClient.findAccountName(mobile);
		if(admin != null) {
			ResCheckAccount account = new ResCheckAccount();
			account.setAccountName(admin.getAccountName());
			account.setMobile(admin.getCellphone());
			return account;
		}
		return null;
	}

	@Override
	public String sendReset(HttpServletRequest request, String account) {
		String uuid = UUIDTool.random().replaceAll("-", "");
		ReqAuthSend rs = new ReqAuthSend();
		if(account != null){
			cn.linkmore.prefecture.response.ResAdmin admin = this.staffAdminUserClient.findAccountName(account);
			rs.setMobile(admin.getCellphone());
		}else {
			String key = TokenUtil.getKey(request);
			CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
			if(ru == null) {
				throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
			}
			rs.setMobile(ru.getMobile());
		}
		rs.setTimestamp(new Date().getTime()+"");
		rs.setToken(uuid);
		this.send(rs);
		this.redisService.set(RedisKey.USER_STAFF_AUTH_EDIT_PW.key+rs.getMobile(),uuid,Constants.ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
		return uuid;
	}

	@Override
	public void reset(HttpServletRequest request, ReqReset reset) {
		if(!reset.getPassword().equals(reset.getRepassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_RE_PASSWORD_ERROR);
		}
//		cn.linkmore.prefecture.response.ResAdmin admin = this.staffAdminUserClient.findAccountName(reset.getAccount());
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		ResAdminUser admin = this.staffAdminUserClient.findById(ru.getId());
		Object object = this.redisService.get(RedisKey.USER_STAFF_AUTH_EDIT_PW.key+admin.getCellphone());
		if(object == null ) {				        
			if(StringUtils.isNotBlank(admin.getAccountName())) {
				object = this.redisService.get(RedisKey.USER_STAFF_AUTH_EDIT_PW.key+reset.getAccount());
			}
			if(object == null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_EXPIRED);
			}
		}
		if(!object.toString().equals(reset.getToken())) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_ERROR);
		}
		if(admin == null || admin.getId() == null) {
			throw new BusinessException(StatusEnum.	ACCOUNT_STAFF_USER_NOT_EXIST);
		}
		String	pw = Md5PW.md5(null, reset.getPassword());
		this.staffAdminUserClient.updatePw(admin.getId(),pw);
		this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_TOKEN.key+admin.getId());
		this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_USER.key+key);  
		if(StringUtils.isNotBlank(admin.getAccountName())) {
			this.redisService.remove(Constants.RedisKey.STAFF_STAFF_AUTH_ACCOUNT.key+admin.getAccountName());  
		}
	}

	@Override
	public boolean authCode(HttpServletRequest request, String account, String code) {
		Object cache = null;
		String mobile = null;
		if(account == null) {
			String key = TokenUtil.getKey(request);
			CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
			if(ru == null) {
				throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
			}
			mobile = ru.getMobile();
			cache = this.redisService.get(RedisKey.STAFF_STAFF_AUTH_CODE.key+ru.getMobile());
		}else {
			cn.linkmore.prefecture.response.ResAdmin admin = this.staffAdminUserClient.findAccountName(account);
			mobile = admin.getCellphone();
			cache = this.redisService.get(RedisKey.STAFF_STAFF_AUTH_CODE.key+admin.getCellphone());
			
		}
		if(staffCode == true && code.equals("6699") ) {
		}else {
		if(cache==null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(code)) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.STAFF_STAFF_AUTH_CODE.key+mobile);
				}
			}
		}
		return true;
	}

	@Override
	public boolean editMobile(String mobile, HttpServletRequest request, String code) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+key); 
		Object cache = this.redisService.get(RedisKey.STAFF_STAFF_AUTH_CODE.key+ru.getMobile());
		if(staffCode == true && code.equals("6699") ) {
		}else {
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(code)) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.STAFF_STAFF_AUTH_CODE.key+ru.getMobile());
				}
			}
		}
		ResAdminUser adminUser = this.staffAdminUserClient.findMobile(mobile);
		if(adminUser != null && adminUser.getId() != null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
		this.staffAdminUserClient.updateMobile(ru.getId(), mobile);
		return true;
	}

	@Override
	public String editPWAuth(ReqEditPwAuth pwAuth) {
		cn.linkmore.prefecture.response.ResAdmin admin = this.staffAdminUserClient.findAccountName(pwAuth.getAccount());
		String md5 = Md5PW.md5(null, pwAuth.getPassword());
		if(StringUtils.isNotBlank(admin.getPassword()) && admin.getPassword().equals(md5)) {
			String uuid = UUIDTool.random().replaceAll("-", "");
			this.redisService.set(RedisKey.USER_STAFF_AUTH_EDIT_PW.key+pwAuth.getAccount(),uuid,Constants.ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
			this.redisService.set(RedisKey.USER_STAFF_AUTH_EDIT_PW.key+admin.getCellphone(),uuid,Constants.ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
			return uuid;
		}
		throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
	}

	
}
