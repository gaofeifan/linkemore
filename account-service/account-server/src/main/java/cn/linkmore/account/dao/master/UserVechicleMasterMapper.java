package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVechicle;
@Mapper
public interface UserVechicleMasterMapper {
    int deleteById(Long id);

    int insert(UserVechicle record);

    int insertSelective(UserVechicle record);

    int updateByIdSelective(UserVechicle record);

    int updateById(UserVechicle record);
}