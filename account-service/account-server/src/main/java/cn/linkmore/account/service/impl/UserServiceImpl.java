package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserAppfansClusterMapper;
import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.cluster.UserVechicleClusterMapper;
import cn.linkmore.account.dao.master.AdminUserMasterMapper;
import cn.linkmore.account.dao.master.UserAppfansMasterMapper;
import cn.linkmore.account.dao.master.UserMasterMapper;
import cn.linkmore.account.dao.master.UserVechicleMasterMapper;
import cn.linkmore.account.entity.AdminUser;
import cn.linkmore.account.entity.Common;
import cn.linkmore.account.entity.CreateCriteria;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.entity.UserVechicle;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.service.CommonService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.constant.RedisKey;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.util.ObjectUtils;
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserAppfansClusterMapper userAppfansClusterMapper;
	@Resource
	private UserAppfansMasterMapper userAppfansMasterMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private UserVechicleClusterMapper userVechicleClusterMapper;
	@Resource
	private UserVechicleMasterMapper userVechicleMasterMapper;
	@Resource
	private UserMasterMapper userMasterMapper;
	@Resource
	private UserClusterMapper userClusterMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private AdminUserMasterMapper adminUserMasterMapper;
	@Override
	public void logout(Long userId) {
		String key = "";
		AdminUser user =(AdminUser) redisTemplate.opsForValue().get(key);
		if(user!=null){
			redisTemplate.delete(key);
			redisTemplate.delete(RedisKey.LINKMORE_APP_USER_ID + user.getId());
		}
	}

	@Override
	public void updateNickname(String nickname, Long userId) {
		User user = getUserCacheKey(userId);
		updateByColumn("nickname", nickname, user.getId());
	}
	
	@Override
	public void updateSex( Integer sex, Long request) {
		User user = getUserCacheKey(request);
		updateByColumn("sex", sex, user.getId());
	}
	
	private void updateByColumn(String column,Object value,Long id) {
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		param.put("column", column);
		param.put("value", value);
		param.put("updateTime", new Date());
		userMasterMapper.updateByColumn(param);
	}
	
	@Override
	public User getUserCacheKey(Long userId){
		return new User();
	}

	@Override
	public void updateVehicle(ReqVehicle req, Long userId) {
		User user = getUserCacheKey(userId);
		UserVechicle vechicle = userVechicleClusterMapper.selectByUserId(user.getId());
		boolean flag = false;
		if(vechicle == null) {
			flag = true;
		}
		UserVechicle object = ObjectUtils.copyObject(req, vechicle);
		object.setUpdateTime(new Date());
		if(flag) {
			object.setCreateTime(new Date());
			object.setUserId(user.getId());	
			this.userVechicleMasterMapper.insert(object);
			return;
		}
		this.userVechicleMasterMapper.updateByIdSelective(object);
	}

	@Override
	public ResUserDetails detail(Long userId) {
		User user = getUserCacheKey(userId);
		Common common = new Common(ResUserDetails.class);
		common.setColumns("uv.model brand_model,uv.brand_id,uv.sex,u.nickname,u.mobile,u.id");
		common.setTable(" t_user_vehicle uv INNER JOIN t_user u ON u.id = uv.user_id AND u.id = "+user.getId());
		List<?> list = this.commonService.selectList(common );
		if(list.size() == 1) {
			ResUserDetails res = (ResUserDetails)list.get(0);
			if(res != null) {
				Object carObj = redisTemplate.opsForValue().get(RedisKey.CAR_BRAND_LIST);
				if(null != carObj){
					//拼装返回  车辆品牌-型号
					String brandModel = "";
					int num = 0;
					for (Object carBrand : (List)carObj) {
						Map m = (Map) carBrand;
						if(m.get("id").toString().equals(res.getBrandModel())){
							brandModel = brandModel + m.get("name");
							for (Object carFirm : (List)m.get("childlist")) {
								Map m2 = (Map)carFirm;
								for (Object carModel : (List)m2.get("carlist")) {
									Map m3 = (Map)carModel;
									if(m3.get("id").toString().equals(res.getBrandModel())){
										brandModel = brandModel+"-"+m3.get("fullname");
										num++;
										break;
									}
								}
								if(num>0){
									break;
								}
							}
							break;
						}
					}
					res.setBrandModel(brandModel);
				}
			}
			UserAppfans af = this.userAppfansClusterMapper.findByUserId(user.getId());
			if(af!=null&&af.getStatus().shortValue()==1){
				res.setWechatId(af.getId());
				res.setWechatUrl(af.getHeadurl());
				res.setWechatName(af.getNickname());
			}
			return res;
		}
		return null;
	}

	@Override
	public void updateMobile(ReqLogin bean, Long userId) {
		User user = getUserCacheKey(userId);
		User dbUser = this.selectByMobile(bean.getMobile());
		if(dbUser == null) {
			this.updateByColumn("mobile", bean.getMobile(), user.getId());
		}else {
			throw new BusinessException();
		}
	}

	@Override
	public void updateWechat(ReqWxLogin bean, Long request) {
		/*JSONObject json = this.getOAuthUserinfo(wechatConfig.getAppId(), wechatConfig.getAppSecret(), bean.getCode()); 
		log.info("json:{}",json.toString()); 
		if (json==null||json.get("errcode") != null) {
			throw new BusinessException(Msg.WECHAT_LOGIN_ERROR); 
		}else{
			String openid = json.getString("openid"); 
			String nickname = json.getString("nickname");
			String headimgurl = json.getString("headimgurl"); 
			String unionid = json.getString("unionid");
			Appfans fans = this.appfansMapper.selectByPrimaryKey(openid);
			User user = this.getCacheUser(request);
			if(fans==null){
				fans = new Appfans();
				fans.setId(openid);
				fans.setHeadurl(headimgurl);
				fans.setNickname(nickname);
				fans.setUnionid(unionid);
				fans.setCreateTime(new Date());
				fans.setStatus((short)1);
				fans.setUserId(user.getId());
				fans.setRegisterStatus((short)0);
				this.appfansMapper.insert(fans);  
			}else{
				fans.setStatus((short)1);
				fans.setUserId(user.getId());
				this.appfansMapper.updateByPrimaryKey(fans);  
			}
		}  
	}*/
	}

	@Override
	public void removeWechat(Long userId) {
		User user = this.getUserCacheKey(userId);
		Common common = new Common(UserAppfans.class);
		common.setColumn("status");
		common.setColumnValue((short)0);
		CreateCriteria criteria = common.createCriteria();
		criteria.equals("user_id", user.getId());
		commonService.updateColumnValue(common);
	}
	
	@SuppressWarnings({ "unchecked" })
	private List<User> selectByColumn(Common common){
		return  (List<User>) this.commonService.selectList(common);
	}
	
	public User selectByMobile(String mobile) {
		Common common = new Common(User.class);
		CreateCriteria criteria = common.createCriteria();
		criteria.equals("mobile", mobile);
		List<User> list = this.selectByColumn(common );
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	public User selectById(Long userId) {
		return this.userClusterMapper.selectById(userId);
	}
	
	
	

}
