package cn.linkmore.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import cn.linkmore.account.controller.app.request.ReqAuthCode;
import cn.linkmore.account.controller.app.request.ReqAuthEditPW;
import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthPW;
import cn.linkmore.account.controller.app.request.ReqAuthRegister;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.request.ReqEditPWAuth;
import cn.linkmore.account.controller.app.request.ReqMobileBind;
import cn.linkmore.account.dao.cluster.UserAppfansClusterMapper;
import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.cluster.UserInfoClusterMapper;
import cn.linkmore.account.dao.cluster.UserStaffClusterMapper;
import cn.linkmore.account.dao.cluster.UserVechicleClusterMapper;
import cn.linkmore.account.dao.cluster.WechatFansClusterMapper;
import cn.linkmore.account.dao.master.AccountMasterMapper;
import cn.linkmore.account.dao.master.AdminUserMasterMapper;
import cn.linkmore.account.dao.master.UserAppfansMasterMapper;
import cn.linkmore.account.dao.master.UserInfoMasterMapper;
import cn.linkmore.account.dao.master.UserMasterMapper;
import cn.linkmore.account.dao.master.UserStaffMasterMapper;
import cn.linkmore.account.dao.master.UserVechicleMasterMapper;
import cn.linkmore.account.entity.Account;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.entity.UserInfo;
import cn.linkmore.account.entity.UserVechicle;
import cn.linkmore.account.entity.WechatFans;
import cn.linkmore.account.request.ReqMessage;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.MessageService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.SmsTemplate;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.task.TaskPool;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

/**
 * 用户实体类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class UserServiceImpl implements UserService {

	public static final String LINKMORE_APP_SMS_CODE = "";

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final static long SPACE = 1000L*60*30; 
	public static final String CAR_BRAND_LIST = "CAR_BRAND_LIST";
	@Autowired
	private MessageService messageService;
	@Autowired
	private AppWechatClient appWechatClient;
	@Autowired
	private WechatMiniClient wechatMiniClient;
	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private SmsClient smsClient;
	@Autowired
	private PushClient pushClient;
	
	@Autowired
	private CouponClient couponClient;
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	@Resource
	private UserAppfansClusterMapper userAppfansClusterMapper;
	@Resource
	private UserAppfansMasterMapper userAppfansMasterMapper;
	@Resource
	private UserVechicleClusterMapper userVechicleClusterMapper;
	@Resource
	private UserVechicleMasterMapper userVechicleMasterMapper;
	@Resource
	private UserMasterMapper userMasterMapper;
	@Resource
	private UserClusterMapper userClusterMapper;
	
	@Autowired
	private UserInfoMasterMapper userInfoMasterMapper;
	
	@Autowired
	private UserInfoClusterMapper userInfoClusterMapper;
	
	@Resource
	private RedisService redisService;
	@Resource
	private AdminUserMasterMapper adminUserMasterMapper;
	
	@Autowired
	private WechatFansClusterMapper wechatFansClusterMapper;
	
	@Autowired
	private UserStaffClusterMapper userStaffClusterMapper;
	
	@Autowired
	private UserStaffMasterMapper userStaffMasterMapper;
	
	@Override
	public void updateNickname(String nickname, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		updateByColumn("nickname", nickname, user.getId());
	}

	@Override
	public void updateSex(Integer sex, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		updateByColumn("sex", sex, ru.getId());
	}

	private void updateByColumn(String column, Object value, Long id) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("column", column);
		param.put("value", value);
		param.put("updateTime", new Date());
		userMasterMapper.updateByColumn(param);
	}


	@Override
	public void updateVehicle(cn.linkmore.account.controller.app.request.ReqUpdateVehicle vehicle, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os")));
		UserVechicle vechicle = userVechicleClusterMapper.findByUserId(ru.getId());
		boolean flag = false;
		if (vechicle == null) {
			vechicle = new UserVechicle();
			flag = true;
		}
		UserVechicle object = ObjectUtils.copyObject(vehicle, vechicle );
		object.setUserId(ru.getId());
		object.setUpdateTime(new Date());
		if (flag) {
			object.setCreateTime(new Date());
			this.userVechicleMasterMapper.insert(object);
			return;
		}
		this.userVechicleMasterMapper.updateByIdSelective(object);
	}

	@Override
	public ResUserDetails detail(Long userId) {
		List<ResUserDetails> list = this.userClusterMapper.findResUserById(userId);
		if (list.size() == 1) {
			UserVechicle vechicle = this.userVechicleClusterMapper.findByUserId(list.get(0).getId());
			ResUserDetails res = (ResUserDetails) list.get(0);
			if (res != null && vechicle != null) {
				Object carObj = redisService.get(RedisKey.COMMON_CAR_BRAND_LIST.key);
				if (null != carObj) {
					// 拼装返回 车辆品牌-型号
					String brandModel = "";
					int num = 0;
					for (Object carBrand : (List<?>) carObj) {
						Map<?,?> m = (Map<?,?>) carBrand;
						if (m.get("id").toString().equals(vechicle.getBrandId().toString())) {
							brandModel = brandModel + m.get("name");
							for (Object carFirm : (List<?>) m.get("childlist")) {
								Map<?,?> m2 = (Map<?,?>) carFirm;
								for (Object carModel : (List<?>) m2.get("carlist")) {
									Map<?,?> m3 = (Map<?,?>) carModel;
									if (m3.get("id").toString().equals(vechicle.getModel().toString())) {
										brandModel = brandModel + "-" + m3.get("fullname");
										num++;
										break;
									}
								}
								if (num > 0) {
									break;
								}
							}
							break;
						}
					}
					res.setBrandModel(brandModel);
				}
			}
			ResUserAppfans af = this.userAppfansClusterMapper.findByUserId(userId);
			if (af != null && af.getStatus().shortValue() == 1) {
				res.setWechatId(af.getId());
				res.setWechatUrl(af.getHeadurl());
				res.setWechatName(af.getNickname());
			}
			return res;
		}
		return null;
	}

	@Override
	public void updateMobile(ReqUpdateMobile bean) {
		ResUser user = this.findByMobile(bean.getMobile());
		if (user == null) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", bean.getUserId());
			param.put("updateTime", new Date());
			param.put("username",bean.getMobile());
			this.userMasterMapper.updateMobile(param);
		} else {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
	}
	
	@Override
	public void updatePassword(String password,String mobile) {
		Map<String,Object> map = new HashMap<>();
		map.put("password", Md5PW.md5(mobile, password));
		map.put("updateTime", new Date());
		map.put("mobile", mobile);
		this.userMasterMapper.updatePassword(map);
	}

	@Override
	public void updateAppfans(ReqUserAppfans bean) {
		ResUser user = this.findById(bean.getUserId());
		UserAppfans fans = this.userAppfansClusterMapper.findById(bean.getId());
		if (fans == null) {
			fans = new UserAppfans();
			fans.setId(bean.getId());
			fans.setHeadurl(bean.getHeadurl());
			fans.setNickname(bean.getNickname());
			fans.setUnionid(bean.getUnionid());
			fans.setCreateTime(new Date());
			fans.setStatus((short) 1);
			fans.setUserId(user.getId());
			fans.setRegisterStatus((short) 0);
			this.userAppfansMasterMapper.insertSelective(fans);
		} else {
			fans.setStatus((short) 1);
			fans.setUserId(user.getId());
			this.userAppfansMasterMapper.updateByIdSelective(fans);
		}
	}

	@Override
	public ResUser findByMobile(String mobile) {
		return this.userClusterMapper.findByMobile(mobile);
	}

	@Override
	public ResUser findById(Long userId) {
		return this.userClusterMapper.findById(userId);
	}


	@Override
	public void insertSelective(User user) {
		this.userMasterMapper.insertSelective(user);
	}
	
	@Override
	public void updateLoginTime(Map<String, Object> param) {
		this.userMasterMapper.updateLoginTime(param);
	}

	@Override
	public void order(Long id) {
		ResUser user = this.userClusterMapper.findById(id);
		if(user!=null) {
			user.setOrderCount(user.getOrderCount()==null?1:user.getOrderCount()+1);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", user.getId());
			param.put("orderCount", user.getOrderCount());
			this.userMasterMapper.orderUpdate(param);
		}
	}
	
	@Override
	public void checkout(Long id) {
		ResUser user = this.userClusterMapper.findById(id);
		if(user!=null) {
			user.setCompleteOrderCount(user.getCompleteOrderCount()==null?1:user.getCompleteOrderCount()+1);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", user.getId());
			param.put("completeOrderCount", user.getCompleteOrderCount());
			this.userMasterMapper.checkoutUpdate(param);
		}
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
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.userClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResPageUser> list = this.userClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	
	@Override
	public int delete(List<Long> ids) {
		accountMasterMapper.deleteByIds(ids);
		userStaffMasterMapper.deleteByIds(ids);
		return this.userMasterMapper.deleteByIds(ids);
	}
	
	
	@Override
	public List<ResPageUser> export(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		List<ResPageUser> list = this.userClusterMapper.export(param);
		return list; 
	}

	@Override
	public void updateRealname(String accountName, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os")));
		this.updateByColumn("realname", accountName, ru.getId());
	}
	private final static String STAFF_CODE = "6699";
	@Override
	public cn.linkmore.account.controller.app.response.ResUser appLogin(ReqAuthLogin rl, HttpServletRequest request) {
		ResUserStaff rus = userStaffClusterMapper.findByMobile(rl.getMobile()); 
		log.info(">>>>>>appLogin rus = {}",JSON.toJSON(rus));
		if(!(rus!=null&&STAFF_CODE.equals(rl.getCode()))) {
			Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE.key+rl.getMobile());
			log.info(">>>>>>appLogin mobile = {}, code = {}",rl.getMobile(), cache);
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
		
		
		ResUser user = this.findByMobile(rl.getMobile());
		if (user == null) {
			user = insertUser(rl.getMobile(), "");
		} else if (user.getStatus().equals("2")) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_LOCKED);
		} else {
			updateLoginTime(user);
		}
		String key = TokenUtil.getKey(request); 
		log.info(">>>>>>appLogin request= {}, key = {}",JSON.toJSON(request.getSession().getId()), JSON.toJSON(key));
		return editResUser(user, request);
	} 

	private cn.linkmore.account.controller.app.response.ResUser editResUser(ResUser user,HttpServletRequest request){
		String key = TokenUtil.getKey(request); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		ru.setId(user.getId());
		ru.setMobile(user.getMobile());
		ru.setToken(key); 
		ru.setSex(user.getSex());
		ru.setRealname(user.getRealname());
		ru.setAlias("u"+user.getId());
		ru.setType(user.getType()==null?0:user.getType());
		ru.setNickname(user.getNickname());
		List<String> tags = new ArrayList<String>();
		tags.add("appuser");
		ru.setTags(tags);
		log.info(">>>>>>appLogin = {}",JSON.toJSON(ru));
		CacheUser u = new CacheUser();
		u.setId(user.getId());
		u.setMobile(user.getMobile());
		u.setToken(key);  
		Token token = this.cacheUser(request, u);  
		if(token!=null) {
			new PushThread(ru.getId().toString(), token).start(); 
		}
		return ru;
	}
	
	private ResUser insertUser(String mobile,String password) {
		ResUser user = new ResUser();
		user.setMobile(mobile);
		user.setUsername(mobile);
		user.setPassword(password);
		user.setUserType("1");
		user.setStatus("1");
		user.setLastLoginTime(new Date());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setIsAppRegister((short) 1);
		user.setAppRegisterTime(new Date());
		user.setIsWechatBind((short) 0);
		user.setFansStatus((short)0);
		this.userMasterMapper.save(user);
		
		Account account = new Account();
		account.setId(user.getId());
		account.setAmount(0.00d);
		account.setUsableAmount(0.00d);
		account.setFrozenAmount(0.00d);
		account.setRechagePaymentAmount(0.00d);
		account.setRechargeAmount(0.00d);
		account.setAccType(1);
		account.setStatus((short) 1);
		account.setOrderAmount(0.00d);
		account.setOrderPaymentAmount(0.00d);
		account.setCreateTime(new Date());
		accountMasterMapper.insert(account);
		couponClient.paySend(user.getId(), 8);
		return user;
	}
	private void updateLoginTime(ResUser user) {
		user.setLastLoginTime(new Date());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", user.getId());
		param.put("lastLoginTime", new Date());
		param.put("updateTime", new Date());
		this.userMasterMapper.updateLoginTime(param);
		this.updateFansStatus((short)0, user.getId());
	}
	@Override
	public cn.linkmore.account.controller.app.response.ResUser login(String code, HttpServletRequest request) {
		ResFans fans = this.appWechatClient.getFans(code);
		if(fans==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_LOGIN_ERROR);
		}    
		UserAppfans db = this.userAppfansClusterMapper.findById(fans.getId());
		if(db==null){
			db = new UserAppfans();
			db.setId(fans.getId());
			db.setHeadurl(fans.getHeadurl());
			db.setNickname(fans.getNickname());
			db.setUnionid(fans.getUnionid());
			db.setCreateTime(new Date()); 
			db.setStatus((short)1);
			db.setRegisterStatus((short)0);
			this.userAppfansMasterMapper.insertSelective(db);  
		}else{
			db.setStatus((short)1);
			this.userAppfansMasterMapper.updateByIdSelective(db);  
		}
		ResUser user = null;
		if(db.getUserId()!=null){
			user = this.userClusterMapper.findById(db.getUserId());
		}
		if(user==null){
			user = this.userClusterMapper.findByMobile(db.getId());
			if(user!=null){
				db.setUserId(user.getId()); 
				this.userAppfansMasterMapper.updateByIdSelective(db);
			}
		}
		if(user==null){
			User u = new User();
			u.setMobile(fans.getId());
			u.setUsername(fans.getId());
			u.setNickname(fans.getNickname());
			u.setPassword("");
			u.setUserType("1");
			u.setStatus("1"); 
			u.setLastLoginTime(new Date());
			u.setCreateTime(new Date());
			u.setUpdateTime(new Date());
			u.setIsAppRegister((short)1);
			u.setAppRegisterTime(new Date());
			u.setIsWechatBind((short)0);
			u.setFansStatus((short)1);
			this.userMasterMapper.insertSelective(u);  
			user = this.userClusterMapper.findById(u.getId());
			Account account = new Account();
			account.setId(u.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insertSelective(account);
			db.setUserId(u.getId());
			this.userAppfansMasterMapper.updateByIdSelective(db);
			couponClient.paySend(user.getId(), 8);
		}  else if(user.getStatus().equals("2")){
			throw new BusinessException(StatusEnum.ACCOUNT_USER_LOCKED);
		} else {
			user.setLastLoginTime(new Date());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
			updateFansStatus((short)1, user.getId());
		}  
		String key = TokenUtil.getKey(request); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		ru.setId(user.getId());
		ru.setNickname(user.getNickname());
		ru.setMobile(user.getMobile());
		ru.setToken(key); 
		ru.setRealname(user.getRealname());
		ru.setSex(user.getSex());
		ru.setAlias("u"+user.getId());
		List<String> tags = new ArrayList<String>();
		tags.add("appuser");
		ru.setTags(tags);
		CacheUser cu = new CacheUser();
		cu.setId(user.getId());
		cu.setMobile(user.getUsername());
		cu.setToken(key); 
		this.cacheUser(request, cu);   
		return ru;
	}

	@Override
	public void logout(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		;
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		this.redisService.remove(appUserFactory.createUserIdRedisKey(ru.getId(), request.getHeader("os")));
		this.redisService.remove(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
	}
	
	private final static ConcurrentHashMap<Long,Long> LOGIN_USER = new ConcurrentHashMap<Long,Long>();
	private Token cacheUser(HttpServletRequest request, CacheUser user) {
		String os = request.getHeader("os");
		Token   last  = null;
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
			last = (Token)this.redisService.get(appUserFactory.createUserIdRedisKey(userId, os));
			log.info("cacheUser syn userId = {}, last = {}",userId, JSON.toJSON(last));
			if(last!=null){ 
				log.info("cacheUser syn openId = {}", user.getOpenId());
				if(user.getOpenId()!=null) { 
					this.redisService.remove(appUserFactory.createOpenIdRedisKey(user.getOpenId(), os));  
				}
				this.redisService.remove(appUserFactory.createUserIdRedisKey(userId, os));
				this.redisService.remove(appUserFactory.createTokenRedisKey(last.getAccessToken(), os));  
				last.setAccessToken(key); 
				log.info("cacheUser syn key = {}, last = {}",key,JSON.toJSON(last));
			}
			user.setClient(new Short(os==null?ClientSource.WXAPP.source+"":os));
			this.redisService.set(appUserFactory.createTokenRedisKey(key, os), user,Constants.ExpiredTime.ACCESS_TOKEN_EXP_TIME.time); 
			
			Token token = new Token();
			token.setClient(new Short(os==null?ClientSource.WXAPP.source+"":os));
			token.setTimestamp(new Date().getTime());
			token.setAccessToken(key);
			log.info("token = {}",JSON.toJSON(token));
			if(user.getClient().intValue()==ClientSource.WXAPP.source && user != null && user.getOpenId() != null) {
				this.redisService.set(appUserFactory.createOpenIdRedisKey(user.getOpenId(), os), token,Constants.ExpiredTime.ACCESS_TOKEN_EXP_TIME.time); 
			}
			this.redisService.set(appUserFactory.createUserIdRedisKey(user.getId(), os), token,Constants.ExpiredTime.ACCESS_TOKEN_EXP_TIME.time); 
			}
		log.info("last = {}",JSON.toJSON(last));
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
		if(this.redisService.exists(RedisKey.USER_APP_AUTH_MOBILE.key+rs.getMobile())) {
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
			TaskPool.getInstance().task(new Runnable() {
				@Override
				public void run() {
					ReqMessage msg = new ReqMessage();
					msg.setCreateTime(new Date());
					msg.setMessageType(Constants.MessageType.LOGIN.type);
					msg.setMobile(rs.getMobile());
					msg.setTemplate("SMS_63275171");
					msg.setParameter(JsonUtil.toJson(param));
					messageService.save(msg);					
				}
			});
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
	@Transactional
	public cn.linkmore.account.controller.app.response.ResUser bindMobile(ReqMobileBind rmb, HttpServletRequest request) {
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
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os")));  
		if(this.redisService.exists(RedisKey.USER_APP_USER_CHANGE_MOBILE.key+ru.getId())) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_CHANGE_MOBILE);
		} 
		ResUser user = this.userClusterMapper.findByMobile(rmb.getMobile());
		log.info("...........bindMobile old ru = {}, new mobile = {}, user ={} ",JSON.toJSON(ru), rmb.getMobile(), JSON.toJSON(user));
		if(ru.getMobile().length() > 11 && user != null) {
			UserAppfans appfans = this.userAppfansClusterMapper.findById(ru.getMobile());
			this.userAppfansMasterMapper.updateFansUserId(user.getId());
			appfans.setUserId(user.getId());
			this.userAppfansMasterMapper.updateByIdSelective(appfans);
			//this.userMasterMapper.deleteById(ru.getId());
			this.updateFansStatus((short)2, user.getId());
			cn.linkmore.account.controller.app.response.ResUser resUser = new cn.linkmore.account.controller.app.response.ResUser();
			resUser.setId(user.getId());
			resUser.setMobile(user.getMobile());
			resUser.setToken(key); 
			resUser.setRealname(user.getRealname());
			resUser.setSex(user.getSex());
			resUser.setAlias("u"+user.getId());
			List<String> tags = new ArrayList<String>();
			resUser.setTags(tags);
			tags.add("appuser");
			CacheUser cu = new CacheUser();
			cu.setId(user.getId());
			cu.setMobile(user.getUsername());
			cu.setToken(key); 
			this.cacheUser(request, cu);
			return resUser;
		}else {
			ReqUpdateMobile rum = new ReqUpdateMobile(); 
			rum.setMobile(rmb.getMobile());
			rum.setUserId(ru.getId());
			this.updateMobile(rum);
			this.updateFansStatus((short)3, ru.getId());
			ResUserDetails details = this.detail(request);
			cn.linkmore.account.controller.app.response.ResUser u = new cn.linkmore.account.controller.app.response.ResUser();
			u.setId(details.getId());
			u.setMobile(rmb.getMobile());
			u.setRealname(details.getRealname());
			u.setSex(details.getSex());
			u.setToken(ru.getToken());
			ru.setMobile(rmb.getMobile());
			this.updateCache(request, ru); 
			this.redisService.set(RedisKey.USER_APP_USER_CHANGE_MOBILE.key+ru.getId(), u.getMobile(), 60*60*24*30); 
			return u;
		}
	}
	
	/**
	 * 更新缓存中的用户信息
	 * @param request 用户请求
	 * @param ru 用户信息
	 */
	private void updateCache(HttpServletRequest request, CacheUser ru){
		String key = TokenUtil.getKey(request); 
		ru.setClient(new Short(request.getHeader("os")==null?ClientSource.WXAPP.source+"":request.getHeader("os")));
		this.redisService.set(appUserFactory.createTokenRedisKey(key, request.getHeader("os")), ru,Constants.ExpiredTime.ACCESS_TOKEN_EXP_TIME.time); 
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

/*	@Override
	public void updateNickname(String nickname, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		ReqUpdateNickname nick = new ReqUpdateNickname();
		nick.setNickname(nickname);
		nick.setUserId(user.getId());
		this.updateNickname(nick);		
	}*/

	/*@Override
	public void updateSex(Integer sex, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		ReqUpdateSex req = new ReqUpdateSex();
		req.setSex(sex);
		req.setUserId(ru.getId());
		this.updateSex(req);
	}*/
/*	@Override
	public void updateVehicle(cn.linkmore.account.controller.app.request.ReqUpdateVehicle vehicle, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		cn.linkmore.account.request.ReqUpdateVehicle object = ObjectUtils.copyObject(vehicle, new cn.linkmore.account.request.ReqUpdateVehicle());
		object.setUserId(ru.getId());
		this.updateVehicle(object);
	}*/
	@Override
	public ResUserDetails detail(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		return this.detail(ru.getId());
	}
	
	@Override
	public void removeWechat(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		if(ru.getMobile().trim().length()>=12) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_BINDING_NOMOBILE);
		}
		this.userAppfansMasterMapper.deleteByUserId(ru.getId());
		Map<String, Object> param = new HashMap<>();
		param.put("column", "wechat");
		param.put("value", null);
		param.put("id", ru.getId());
		param.put("updateTime", new Date());
		this.userMasterMapper.updateByColumn(param);
	}
/*	@Override
	public void updateRealname(String accountName, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
		ReqUpdateAccount account = new ReqUpdateAccount();
		account.setRealname(accountName);
		account.setUserId(ru.getId());
		this.updateRealname(account);
	}*/
	
	private CacheUser getCacheUser(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		return (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os"))); 
	}

	@Override
	public ResUser getUserByUserName(String userName) {
		return this.userClusterMapper.getUserByUserName(userName);
	}

	@Override
	public void bindWechat(String code, HttpServletRequest request) { 
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(key, request.getHeader("os")));  
		if(ru.getMobile().trim().length()>=12) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_BINDING_NOMOBILE);
		}
		ResFans fans = this.appWechatClient.getFans(code);
		if(fans==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_BINDING_ERROR);
		}    
		UserAppfans db = this.userAppfansClusterMapper.findById(fans.getId());
		if(db!=null&&db.getUserId().longValue()!=ru.getId().longValue()) {
			throw new BusinessException(StatusEnum.ACCOUNT_WECHAT_BINDING_ERROR);
		}
		if(db==null){
			db = new UserAppfans();
			db.setId(fans.getId());
			db.setHeadurl(fans.getHeadurl());
			db.setNickname(fans.getNickname());
			db.setUnionid(fans.getUnionid());
			db.setCreateTime(new Date()); 
			db.setUserId(ru.getId());
			db.setStatus((short)1);
			db.setRegisterStatus((short)0);
			this.userAppfansMasterMapper.insertSelective(db);  
		}else{  
			db.setStatus((short)1); 
			db.setRegisterStatus((short)1);
			this.userAppfansMasterMapper.updateByIdSelective(db);  
		}   
	}

	
	private UserInfo saveUserInfo(ResMiniSession rms) {
		UserInfo ui = null;
		WechatFans fans = this.wechatFansClusterMapper.findByUnionid(rms.getUnionid());
		if (fans != null) {
			ui = new UserInfo();
			ui.setCreateTime(new Date());
			ui.setAvatarurl(fans.getHeadimagurl());
			ui.setGender(fans.getSex().shortValue());
			ui.setCity(fans.getCity());
			ui.setNickName(fans.getNickname());
			ui.setUnionId(rms.getUnionid());
			if (fans.getUid() != null) {
				ui.setUserId(fans.getUid());
				ui.setBindTime(ui.getCreateTime());
			}
			ui.setId(rms.getOpenid());
			this.userInfoMasterMapper.save(ui);
		} else {
			ui = new UserInfo();
			ui.setId(rms.getOpenid());
			ui.setCreateTime(new Date());
			ui.setUnionId(rms.getUnionid());
			this.userInfoMasterMapper.save(ui);
		}		return ui;
	}
	@Override
	public cn.linkmore.account.controller.app.response.ResUser mini(String code, HttpServletRequest request) {
		ResMiniSession rms = wechatMiniClient.getSession(code);
		log.info("rms:{}",JsonUtil.toJson(rms));
		CacheUser cu = new CacheUser(); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		if(rms != null) {
			UserInfo ui = this.userInfoClusterMapper.find(rms.getOpenid());
			if (ui == null) {
				ui = this.saveUserInfo(rms);
			}
			if (ui.getUserId() != null) {
				ResUser user = this.userClusterMapper.findById(ui.getUserId());
				if (user != null) {
					ru.setId(user.getId());
					ru.setMobile(user.getUsername());
					cu.setMobile(user.getMobile());
				}
			}
			ru.setAlias(rms.getOpenid());
			String key = TokenUtil.getKey(request);  
			ru.setToken(key);  
			cu.setId(ui.getUserId());
			ru.setNickname(ui.getNickName());
			cu.setOpenId(rms.getOpenid());
			cu.setToken(key); 
			cu.setSession(rms.getSession_key());
			cu.setClient((short)ClientSource.WXAPP.source);
			this.cacheUser(request, cu);
		}else {
			log.info("...........................mini progrem login error........................");
			return null;
		}
		return ru;
	}
	
	@Override
	public cn.linkmore.account.controller.app.response.ResUser miniPlus(String code, Integer alias,HttpServletRequest request) {
		
		ResMiniSession rms = wechatMiniClient.getSessionPlus(code, alias);
		
		log.info("rms:{}",JsonUtil.toJson(rms));
		UserInfo ui = this.userInfoClusterMapper.find(rms.getOpenid());
		if (ui == null) {
			ui = this.saveUserInfo(rms);
		}
		CacheUser cu = new CacheUser(); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		if (ui.getUserId() != null) {
			ResUser user = this.userClusterMapper.findById(ui.getUserId());
			if (user != null) {
				ru.setId(user.getId());
				ru.setMobile(user.getUsername());
				cu.setMobile(user.getMobile());
			}
		}
		ru.setAlias(rms.getOpenid());
		String key = TokenUtil.getKey(request);  
		ru.setToken(key);  
		cu.setId(ui.getUserId());
		cu.setOpenId(rms.getOpenid());
		cu.setToken(key); 
		cu.setSession(rms.getSession_key());
		cu.setClient((short)ClientSource.WXAPP.source);
		this.cacheUser(request, cu);
		return ru;
	}
	
	@Override
	public cn.linkmore.account.controller.app.response.ResUser bindWechatMobile(String mobile,
			HttpServletRequest request) {
		ResUser user = this.findByMobile(mobile);
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		if (user == null) {
			user = new ResUser();
			user.setMobile(mobile);
			user.setUsername(mobile);
			user.setPassword("");
			user.setUserType("1");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 1);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			this.userMasterMapper.insert(user);
			Account account = new Account();
			account.setId(user.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insert(account);
			ru.setNewUserFlag((short)1);
			couponClient.paySend(user.getId(), 8);
			
		} else if (user.getStatus().equals("2")) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_LOCKED);
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
			this.updateFansStatus((short)0, user.getId());
			//throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
		CacheUser cu = this.getCacheUser(request);
		UserInfo ui = this.userInfoClusterMapper.find(cu.getOpenId());
		ui.setUserId(user.getId());
		ui.setBindTime(new Date());
		this.userInfoMasterMapper.update(ui);
		String key = TokenUtil.getKey(request); 
		
		ru.setId(user.getId());
		ru.setMobile(user.getUsername());
		ru.setToken(key); 
		ru.setSex(user.getSex());
		ru.setRealname(user.getRealname());
		ru.setAlias("u"+user.getId());
		List<String> tags = new ArrayList<String>();
		tags.add("miniuser");
		ru.setTags(tags); 
		cu.setMobile(user.getUsername()); 
		cu.setId(user.getId());
		this.cacheUser(request, cu);
		return ru; 
	}

	@Override
	public cn.linkmore.account.controller.app.response.ResUser bindNormalMobile(ReqMobileBind rmb,
			HttpServletRequest request) {
		if(!("6666".equals(rmb.getCode()))) {
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
		} 
		return this.bindWechatMobile(rmb.getMobile(), request);
	}
	
	private void updateFansStatus(Short fansStaus,Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("updateTime", new Date());
		param.put("fansStatus",fansStaus);
		this.userMasterMapper.updateFansStatus(param);
	}

	@Override
	public Long getUserIdByMobile(String mobile) {
		ResUser user = this.userClusterMapper.findByMobile(mobile);
		if(user == null) {
			user = new ResUser();
			user.setMobile(mobile);
			user.setUsername(mobile);
			user.setPassword("");
			user.setUserType("1");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 1);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short)0);
			this.userMasterMapper.save(user);
			Account account = new Account();
			account.setId(user.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insert(account);
		}
		return user.getId();
	}

	@Override
	public Map<String,Long> getUserMapByMobile(List<String> mobile) {
		Map<String,Long> ids = new HashMap<>();
		List<ResUser> users = this.userClusterMapper.findListByMobile(mobile);
		for (String name : mobile) {
			for (ResUser resUser : users) {
				if(name.equals(resUser.getUsername())) {
					ids.put(name, resUser.getId());
					break;
				}
			}
			ResUser user = new ResUser();
			user.setMobile(name);
			user.setUsername(name);
			user.setPassword("");
			user.setUserType("1");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 1);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short)0);
			this.userMasterMapper.save(user);
			Account account = new Account();
			account.setId(user.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insert(account);
			ids.put(name, user.getId());
		}
		return ids;
	}

	@Override
	public ResUser save(ResUser user) {
		log.info("user {}",JSON.toJSON(user));
		this.userMasterMapper.save(user);
		log.info("userId {}",user.getId());
		return user;
	}

	@Override
	public List<ResUser> findAll() {
		return this.userClusterMapper.findAll();
	}

	@Override
	public cn.linkmore.account.controller.app.response.ResUser loginPW(ReqAuthPW pw,HttpServletRequest request) {
		ResUser resUser = this.findByMobile(pw.getMobile());
		if(resUser == null){
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}
		if(StringUtils.isBlank(resUser.getPassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
		}
		if(resUser.getPassword().equals(Md5PW.md5(pw.getMobile(), pw.getPassword()))) {
			updateLoginTime(resUser);
			String key = TokenUtil.getKey(request); 
			log.info(">>>>>>appLogin request= {}, key = {}",JSON.toJSON(request.getSession().getId()), JSON.toJSON(key));
			return editResUser(resUser, request);
		}
		throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
	}
	
	
	@Override
	public cn.linkmore.account.controller.app.response.ResUser register(ReqAuthRegister register,
			HttpServletRequest request) {
		ResUser resUser = this.findByMobile(register.getMobile());
		if(resUser != null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
		if(!register.getPassword().equals(register.getRepassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_RE_PASSWORD_ERROR);
		}
		Object object = this.redisService.get(RedisKey.USER_APP_AUTH_EDIT_PW.key+register.getMobile());
		if(object == null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_EXPIRED);
		}
		if(!object.toString().equals(register.getToken())) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_ERROR);
		}
		String pw = Md5PW.md5(register.getMobile(), register.getPassword());
		ResUser user = insertUser(register.getMobile(), pw);
		return editResUser(user, request);
	}


	@Override
	public Boolean authCode(ReqAuthCode authCode) {
		ResUserStaff rus = userStaffClusterMapper.findByMobile(authCode.getMobile()); 
		if(!(rus!=null&&STAFF_CODE.equals(authCode.getCode()))) {
			Object cache = this.redisService.get(RedisKey.USER_APP_AUTH_CODE.key+authCode.getMobile());
			log.info(">>>>>>appLogin mobile = {}, code = {}",authCode.getMobile(), cache);
			if(cache==null) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
			}else {
				if(!cache.toString().equals(authCode.getCode())) {
					throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
				}else {
					this.redisService.remove(RedisKey.USER_APP_AUTH_CODE.key+authCode.getMobile());
				}
			}
		}
		return true;
	}


	@Override
	public Boolean editPW(ReqAuthEditPW pw,HttpServletRequest request) {
		if(!pw.getPassword().equals(pw.getRepassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_RE_PASSWORD_ERROR);
		}
		Object object = this.redisService.get(RedisKey.USER_APP_AUTH_EDIT_PW.key+pw.getMobile());
		if(object == null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_EXPIRED);
		}
		if(!object.toString().equals(pw.getToken())) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_CODE_ERROR);
		}
		ResUser user = this.findByMobile(pw.getMobile());
		if(user!= null && user.getPassword().equals(Md5PW.md5(pw.getMobile(), pw.getPassword()))) {
			throw new BusinessException(StatusEnum.USER_APP_PASSWORD_ERROR);
		}
		this.updatePassword(pw.getPassword(), pw.getMobile());
//		String os = request.getHeader("os");
		String accessToken = TokenUtil.getKey(request);
		this.redisService.remove(RedisKey.USER_APP_AUTH_EDIT_PW.key+pw.getMobile());
		this.redisService.remove(appUserFactory.createUserIdRedisKey(user.getId(), "1"));
		this.redisService.remove(appUserFactory.createTokenRedisKey(accessToken, "1"));  
		this.redisService.remove(appUserFactory.createUserIdRedisKey(user.getId(), "0"));
		this.redisService.remove(appUserFactory.createTokenRedisKey(accessToken, "0"));  
		return true;
	}

	@Override
	public String sendPW(ReqAuthSend rs, HttpServletRequest request) {
		this.send(rs);
		String uuid = UUIDTool.random().replaceAll("-", "");
		this.redisService.set(RedisKey.USER_APP_AUTH_EDIT_PW.key+rs.getMobile(),uuid,Constants.ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
		return uuid;
	}

	@Override
	public String editPWAuth(ReqEditPWAuth pwAuth) {
		ResUser user = this.findByMobile(pwAuth.getMobile());
		if(user == null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}
		if(!(StringUtils.isNotBlank(user.getPassword()) && user.getPassword().equals(Md5PW.md5(pwAuth.getMobile(), pwAuth.getPassword())))) {
			throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
		}
		String uuid = UUIDTool.random().replaceAll("-", "");
		this.redisService.set(RedisKey.USER_APP_AUTH_EDIT_PW.key+pwAuth.getMobile(),uuid,Constants.ExpiredTime.COUPON_SEND_COUNT_EXP_TIME.time);
		return uuid;
	}

	@Override
	public void reset(List<Long> ids, String password) {
		List<ResUser> users = this.userClusterMapper.findByIds(ids);
		users.stream().forEach(u -> u.setPassword(Md5PW.md5(u.getMobile(), password)));
		this.userMasterMapper.updateIds(users);
	}

	@Override
	public Boolean authIsNew(String mobile) {
		ResUser user = this.findByMobile(mobile);
		return user == null ? true : false;
	}

	@Override
	public Long getUserMapByMobile(String mobile, String username) {
		ResUser user = this.userClusterMapper.findByMobile(mobile);
		if(user == null) {
			user = new ResUser();
			user.setMobile(mobile);
			user.setUsername(mobile);
			user.setPassword("");
			user.setUserType("1");
			user.setStatus("1");
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short) 1);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short) 0);
			user.setFansStatus((short)0);
			user.setNickname(username);
			this.userMasterMapper.save(user);
			Account account = new Account();
			account.setId(user.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insert(account);
		}
		return user.getId();
	}
	
	
}
class UUIDTool{
	public static String random() {
		return UUID.randomUUID().toString();
	}
}
class Md5PW{
	private static final String LINKEMORE = "LINKEMORE";
	public static String md5(String mobile ,String password) {
		if(mobile == null) {
			mobile = "";
		}
		String hex = DigestUtils.md5Hex(LINKEMORE+mobile+password);
		return hex;
	}
}


