package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.AccountHistory;

public interface AccountHistoryClusterMapper {

    AccountHistory findById(Long id);

}