package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.WalletDetail;
@Mapper
public interface WalletDetailClusterMapper {

    WalletDetail findById(Long id);
    
}