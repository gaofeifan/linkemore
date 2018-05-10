package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.User;
@Mapper
public interface UserClusterMapper {
    User selectByPrimaryKey(Long id);
    
    User selectByMobile(String mobile);

    List<User> selectAll();
}