package cn.linkmore.account.service;

import cn.linkmore.account.entity.UserAppfans;

public interface UserAppfansService {

	UserAppfans selectById(String id);

	UserAppfans selectByUserId(Long userId);

	void insertSelective(UserAppfans record);

	void updateByIdSelective(UserAppfans record);

	void updateStatusByUserId(Long userId, int i);


}
