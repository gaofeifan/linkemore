package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVersion;

@Mapper
public interface UserVersionMasterMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserVersion record);

    int updateByPrimaryKey(UserVersion record);
}