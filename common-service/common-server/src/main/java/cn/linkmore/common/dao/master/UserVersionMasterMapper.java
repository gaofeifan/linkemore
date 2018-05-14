package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UserVersion;
@Mapper
public interface UserVersionMasterMapper {
    int deleteById(Long userId);

    int insert(UserVersion record);

    int insertSelective(UserVersion record);

    int updateByIdSelective(UserVersion record);

    int updateById(UserVersion record);
}