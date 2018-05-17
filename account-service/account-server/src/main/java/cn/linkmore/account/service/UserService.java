package cn.linkmore.account.service;


import cn.linkmore.account.entity.User;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ReqNickname;
import cn.linkmore.account.response.ReqSex;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;

public interface UserService {
	
	void logout(Long request);
	
	void updateVehicle(ReqVehicle req);

	ResUserDetails detail(Long userId);

	void updateMobile(ReqLogin bean);

	void updateWechat(ReqWxLogin bean);

	void removeWechat(Long userId);

	ResUser getUserCacheKey(Long userId);

	User selectByMobile(String mobile);

	User selectById(Long userId);

	void updateNickname(ReqNickname nickname);

	void updateSex(ReqSex sex);

}
