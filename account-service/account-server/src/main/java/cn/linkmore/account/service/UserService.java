package cn.linkmore.account.service;


import java.util.Map;

import cn.linkmore.account.entity.User;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUpdateWechat;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;

public interface UserService {
	
	void logout(Long request);
	
	void updateVehicle(ReqUpdateVehicle req);

	ResUserDetails detail(Long userId);

	void updateMobile(ReqUpdateMobile bean);

	void updateWechat(ReqUpdateWechat bean);

	void removeWechat(Long userId);

	ResUser getUserCacheKey(Long userId);

	User selectByMobile(String mobile);

	User selectById(Long userId);

	void updateNickname(ReqUpdateNickname nickname);

	void updateSex(ReqUpdateSex sex);

	void insertSelective(User user);

	void updateLoginTime(Map<String, Object> param);

	ResUserLogin appLogin(String mobile);

}
