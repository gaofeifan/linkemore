package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.WalletDetail;

public interface WalletDetailMasterMapper {
    int delete(Long id);

    int save(WalletDetail record);

    int update(WalletDetail record);
}