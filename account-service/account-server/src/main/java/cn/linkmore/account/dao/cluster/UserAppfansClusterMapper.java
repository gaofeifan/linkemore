package cn.linkmore.account.dao.cluster;


import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserAppfans;
import feign.Param;
@Mapper
public interface UserAppfansClusterMapper {

    UserAppfans selectById(String id);

	UserAppfans selectByUserId(@Param("userId")Long userId);


}