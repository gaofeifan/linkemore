package cn.linkmore.account.service;

import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;

public interface UserAppfansService {

	UserAppfans selectById(String id);

	ResUserAppfans selectByUserId(Long userId);

	void insertSelective(UserAppfans record);

	void updateByIdSelective(UserAppfans record);

	void updateStatusByUserId(Long userId, int i);

	ResUserLogin wxLogin(ReqUserAppfans appfans);


}
