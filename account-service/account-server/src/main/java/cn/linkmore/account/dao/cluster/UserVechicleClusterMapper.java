package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVechicle;
@Mapper
public interface UserVechicleClusterMapper {

    UserVechicle selectById(Long id);

    UserVechicle selectByUserId(Long userId);

}