package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UserVersion;
@Mapper
public interface UserVersionClusterMapper {

    UserVersion selectById(Long userId);

}