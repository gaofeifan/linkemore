package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.Account;

public interface AccountClusterMapper {

    Account findById(Long id);

}