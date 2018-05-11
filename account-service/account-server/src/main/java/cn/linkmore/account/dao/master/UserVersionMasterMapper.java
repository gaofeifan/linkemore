package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVersion;
@Mapper
public interface UserVersionMasterMapper {
    int deleteById(Long userId);

    int insert(UserVersion record);

    int insertSelective(UserVersion record);

    int updateByIdSelective(UserVersion record);

    int updateById(UserVersion record);
}