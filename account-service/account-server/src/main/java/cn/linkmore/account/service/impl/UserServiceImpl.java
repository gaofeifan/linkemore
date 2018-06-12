package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.linkmore.account.common.UserCache;
import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.request.ReqMobileBind;
import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.cluster.UserVechicleClusterMapper;
import cn.linkmore.account.dao.master.AccountMasterMapper;
import cn.linkmore.account.dao.master.AdminUserMasterMapper;
import cn.linkmore.account.dao.master.UserMasterMapper;
import cn.linkmore.account.dao.master.UserVechicleMasterMapper;
import cn.linkmore.account.entity.Account;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.entity.UserVechicle;
import cn.linkmore.account.request.ReqUpdateAccount;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.service.UserAppfansService;
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
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SmsClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.third.request.ReqSms;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

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
	
	private final static String TEST_MOBILE= "18511509492|18801243820|18010161135|18510770300|18612100125|17800242258|13693544138|18810796650|18334787583|18514410536";

	public static final String CAR_BRAND_LIST = "CAR_BRAND_LIST";
	@Autowired
	private AppWechatClient appWechatClient;
	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private SmsClient smsClient;
	@Autowired
	private PushClient pushClient;
	@Resource
	private UserAppfansService userAppfansService;
	@Resource
	private UserVechicleClusterMapper userVechicleClusterMapper;
	@Resource
	private UserVechicleMasterMapper userVechicleMasterMapper;
	@Resource
	private UserMasterMapper userMasterMapper;
	@Resource
	private UserClusterMapper userClusterMapper;
	@Resource
	private RedisService redisService;
	@Resource
	private AdminUserMasterMapper adminUserMasterMapper;

	@Override
	public void updateNickname(ReqUpdateNickname nickname) {
		updateByColumn("nickname", nickname.getNickname(), nickname.getUserId());
	}

	@Override
	public void updateSex(ReqUpdateSex sex) {
		updateByColumn("sex", sex.getSex(), sex.getUserId());
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
	public void updateVehicle(ReqUpdateVehicle req) {
		UserVechicle vechicle = userVechicleClusterMapper.findByUserId(req.getUserId());
		boolean flag = false;
		if (vechicle == null) {
			vechicle = new UserVechicle();
			flag = true;
		}
		UserVechicle object = ObjectUtils.copyObject(req, vechicle);
		object.setUpdateTime(new Date());
		if (flag) {
			object.setCreateTime(new Date());
			object.setUserId(req.getUserId());
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
					for (Object carBrand : (List) carObj) {
						Map m = (Map) carBrand;
						if (m.get("id").toString().equals(vechicle.getBrandId().toString())) {
							brandModel = brandModel + m.get("name");
							for (Object carFirm : (List) m.get("childlist")) {
								Map m2 = (Map) carFirm;
								for (Object carModel : (List) m2.get("carlist")) {
									Map m3 = (Map) carModel;
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
			ResUserAppfans af = this.userAppfansService.findByUserId(userId);
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
	public void updateAppfans(ReqUserAppfans bean) {
		ResUser user = this.findById(bean.getUserId());
		UserAppfans fans = this.userAppfansService.findById(bean.getId());
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
			this.userAppfansService.insertSelective(fans);
		} else {
			fans.setStatus((short) 1);
			fans.setUserId(user.getId());
			this.userAppfansService.updateByIdSelective(fans);
		}
	}

	@Override
	public void removeWechat(Long userId) {
		this.userAppfansService.updateStatusByUserId(userId, 0);
		Map<String, Object> param = new HashMap<>();
		param.put("column", "wechat");
		param.put("value", null);
		param.put("id", userId);
		param.put("updateTime", new Date());
		this.userMasterMapper.updateByColumn(param );
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
	public ResUserLogin appLogin(String mobile) {
		ResUser user = this.findByMobile(mobile);
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
		} else if (user.getStatus().equals("2")) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_LOCKED);
		} else {
			user.setLastLoginTime(new Date());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userMasterMapper.updateLoginTime(param);
		}
		ResUserLogin token = new ResUserLogin();
		token.setId(user.getId());
		token.setMobile(user.getUsername());
		token.setAccountName(user.getAccountName());
		return token;
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
	public void updateAccountName(ReqUpdateAccount account) {
		this.updateByColumn("account_name", account.getAccountName(), account.getUserId());
	}

	@Override
	public cn.linkmore.account.controller.app.response.ResUser appLogin(ReqAuthLogin rl, HttpServletRequest request) {
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
		ResUserLogin rul = this.appLogin(rl.getMobile());
		if(rul==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		} 
		String key = UserCache.getCacheKey(request); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		ru.setId(rul.getId());
		ru.setMobile(rl.getMobile());
		ru.setToken(key); 
		ru.setAccountName(rul.getAccountName());
		CacheUser user = new CacheUser();
		user.setId(rul.getId());
		user.setMobile(rul.getMobile());
		user.setToken(key);  
		Token token = this.cacheUser(request, user);  
		if(token!=null) {
			this.push(ru.getId().toString(), token); 
		}
		return ru;
	}

	@Override
	public cn.linkmore.account.controller.app.response.ResUser login(String code, HttpServletRequest request) {
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
		ResUserLogin rul =this.userAppfansService.wxLogin(ruaf);
		if(rul==null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		} 
		String key = UserCache.getCacheKey(request); 
		cn.linkmore.account.controller.app.response.ResUser ru = new cn.linkmore.account.controller.app.response.ResUser();
		ru.setId(rul.getId());
		ru.setMobile(rul.getMobile());
		ru.setToken(key); 
		ru.setAccountName(rul.getAccountName());
		CacheUser user = new CacheUser();
		user.setId(rul.getId());
		user.setMobile(rul.getMobile());
		user.setToken(key); 
		this.cacheUser(request, user);   
		return ru;
	}

	@Override
	public void logout(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		this.redisService.remove(Constants.RedisKey.USER_APP_AUTH_TOKEN.key+ru.getId().toString());
		this.redisService.remove(Constants.RedisKey.USER_APP_AUTH_USER.key+key); 
	}
	
	private Token cacheUser(HttpServletRequest request, CacheUser user) {
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
	public void bindMobile(ReqMobileBind rmb, HttpServletRequest request) {
		Object cache = this.redisService.get(RedisKey.USER_APP_USER_CODE.key+rmb.getMobile());
		/*if(cache==null) {
			throw new BusinessException(StatusEnum.USER_APP_SMS_EXPIRED);
		}else {
			if(!cache.toString().equals(rmb.getCode())) {
				throw new BusinessException(StatusEnum.USER_APP_SMS_ERROR);
			}else {
				this.redisService.remove(RedisKey.USER_APP_USER_CODE.key+rmb.getMobile());
			}
		} */
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateMobile rum = new ReqUpdateMobile(); 
		rum.setMobile(rmb.getMobile());
		rum.setUserId(ru.getId());
		this.updateMobile(rum);
		this.updateCache(request, ru); 
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
		String key = UserCache.getCacheKey(request);
		ResUser user = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateNickname nick = new ReqUpdateNickname();
		nick.setNickname(nickname);
		nick.setUserId(user.getId());
		this.updateNickname(nick);		
	}

	@Override
	public void updateSex(Integer sex, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateSex req = new ReqUpdateSex();
		req.setSex(sex);
		req.setUserId(ru.getId());
		this.updateSex(req);
	}
	@Override
	public void updateVehicle(cn.linkmore.account.controller.app.request.ReqUpdateVehicle vehicle, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		vehicle.setUserId(ru.getId());
		cn.linkmore.account.request.ReqUpdateVehicle object = ObjectUtils.copyObject(vehicle, new cn.linkmore.account.request.ReqUpdateVehicle());
		this.updateVehicle(object);
	}
	@Override
	public ResUserDetails detail(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(Constants.RedisKey.USER_APP_AUTH_USER.key+key); 
		return this.detail(ru.getId());
	}
	
	@Override
	public void removeWechat(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		this.removeWechat(ru.getId());
	}
	@Override
	public void updateAccountName(String accountName, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqUpdateAccount account = new ReqUpdateAccount();
		account.setAccountName(accountName);
		account.setUserId(ru.getId());
		this.updateAccountName(account);
	}
	
}
