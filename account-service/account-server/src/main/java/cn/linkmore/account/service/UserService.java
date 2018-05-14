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

	void updateVehicle(ReqVehicle req, Long userId);

	ResUserDetails detail(Long userId);

	void updateMobile(ReqLogin bean, Long userId);

	void updateWechat(ReqWxLogin bean, Long userId);

	void removeWechat(Long userId);

	User getUserCacheKey(Long userId);

}
