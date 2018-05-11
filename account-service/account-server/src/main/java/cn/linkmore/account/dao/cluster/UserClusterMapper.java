package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.User;
@Mapper
public interface UserClusterMapper {

    User selectById(Long id);

}