package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.WalletDetail;
@Mapper
public interface WalletDetailMasterMapper {
    int delete(Long id);

    int save(WalletDetail record);

    int update(WalletDetail record);
}