package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.AccountHistory;
@Mapper
public interface AccountHistoryClusterMapper {

    AccountHistory findById(Long id);

}