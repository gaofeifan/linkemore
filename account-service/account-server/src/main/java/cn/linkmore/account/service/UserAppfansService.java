package cn.linkmore.account.service;

import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.response.ResUserAppfans;

public interface UserAppfansService {

	UserAppfans selectById(String id);

	ResUserAppfans selectByUserId(Long userId);

	void insertSelective(UserAppfans record);

	void updateByIdSelective(UserAppfans record);

	void updateStatusByUserId(Long userId, int i);


}
