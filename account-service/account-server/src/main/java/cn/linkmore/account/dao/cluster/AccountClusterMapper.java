package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Account;
@Mapper
public interface AccountClusterMapper {

    Account selectById(Long id);

}