package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.UserAppfansService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.SmsClient;
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

	public static final String CAR_BRAND_LIST = "CAR_BRAND_LIST";
	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private SmsClient smsClient;
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
		ResUser user = getUserCacheKey(nickname.getUserId());
		updateByColumn("nickname", nickname.getNickname(), user.getId());
	}

	@Override
	public void updateSex(ReqUpdateSex sex) {
		ResUser user = getUserCacheKey(sex.getUserId());
		updateByColumn("sex", sex.getSex(), user.getId());
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
	public ResUser getUserCacheKey(Long userId) {
		User u = this.selectById(userId);
		if(u != null) {
			return ObjectUtils.copyObject(u, new ResUser());
		}
		return null;
	}

	@Override
	public void updateVehicle(ReqUpdateVehicle req) {
		ResUser user = getUserCacheKey(req.getUserId());
		UserVechicle vechicle = userVechicleClusterMapper.selectByUserId(user.getId());
		boolean flag = false;
		if (vechicle == null) {
			flag = true;
		}
		UserVechicle object = ObjectUtils.copyObject(req, vechicle);
		object.setUpdateTime(new Date());
		if (flag) {
			object.setCreateTime(new Date());
			object.setUserId(user.getId());
			this.userVechicleMasterMapper.insert(object);
			return;
		}
		this.userVechicleMasterMapper.updateByIdSelective(object);
	}

	@Override
	public ResUserDetails detail(Long userId) {
		List<ResUserDetails> list = this.userClusterMapper.selectResUserById(userId);
		if (list.size() == 1) {
			ResUserDetails res = (ResUserDetails) list.get(0);
			if (res != null) {
				Object carObj = redisService.get(CAR_BRAND_LIST);
				if (null != carObj) {
					// 拼装返回 车辆品牌-型号
					String brandModel = "";
					int num = 0;
					for (Object carBrand : (List) carObj) {
						Map m = (Map) carBrand;
						if (m.get("id").toString().equals(res.getBrandModel())) {
							brandModel = brandModel + m.get("name");
							for (Object carFirm : (List) m.get("childlist")) {
								Map m2 = (Map) carFirm;
								for (Object carModel : (List) m2.get("carlist")) {
									Map m3 = (Map) carModel;
									if (m3.get("id").toString().equals(res.getBrandModel())) {
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
			ResUserAppfans af = this.userAppfansService.selectByUserId(userId);
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
		User user = this.selectByMobile(bean.getMobile());
		if (user == null) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", bean.getUserId());
			param.put("updateTime", new Date());
			param.put("mobile",bean.getMobile());
			this.userMasterMapper.updateMobile(param);
		} else {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
	}

	@Override
	public void updateAppfans(ReqUserAppfans bean) {
		User user = this.selectById(bean.getUserId());
		UserAppfans fans = this.userAppfansService.selectById(bean.getId());
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
	public User selectByMobile(String mobile) {
		return this.userClusterMapper.selectByMobile(mobile);
	}

	@Override
	public User selectById(Long userId) {
		return this.userClusterMapper.selectById(userId);
	}

	@Override
	public ResUserLogin appLogin(String mobile) {
		User user = this.selectByMobile(mobile);
		if (user == null) {
			user = new User();
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
			throw new BusinessException();
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

}
