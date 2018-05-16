package cn.linkmore.account.service;


import cn.linkmore.account.entity.User;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ResUserDetails;

public interface UserService {
	
	void logout(Long request);
	
	void updateNickname(String nickname, Long userId);

	void updateSex(Integer sex, Long userId);

	void updateVehicle(ReqVehicle req);

	ResUserDetails detail(Long userId);

	void updateMobile(ReqLogin bean);

	void updateWechat(ReqWxLogin bean);

	void removeWechat(Long userId);

	User getUserCacheKey(Long userId);

	User selectByMobile(String mobile);

	User selectById(Long userId);

}
