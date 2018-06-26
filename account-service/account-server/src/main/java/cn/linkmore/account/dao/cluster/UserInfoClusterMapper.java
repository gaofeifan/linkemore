package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserInfo;

@Mapper
public interface UserInfoClusterMapper { 
	
    UserInfo find(String id); 
}