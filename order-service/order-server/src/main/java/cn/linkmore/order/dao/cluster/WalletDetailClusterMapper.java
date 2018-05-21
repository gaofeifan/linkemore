package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.WalletDetail;

public interface WalletDetailClusterMapper {

    WalletDetail findById(Long id);
    
}