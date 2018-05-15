package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.UserAppfans;
@Mapper
public interface UserAppfansMasterMapper {
    int deleteById(String id);

    int insert(UserAppfans record);

    int insertSelective(UserAppfans record);

    int updateByIdSelective(UserAppfans record);

    int updateById(UserAppfans record);

	void updateStatusByUserId(@Param("userId") Long userId,@Param("status") Integer status);

}