package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserVersion;
@Mapper
public interface UserVersionClusterMapper {

    UserVersion selectById(Long userId);

}