package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.RechargeAmount;

public interface RechargeAmountMasterMapper {
    int delete(Long id);

    int save(RechargeAmount record);

    int update(RechargeAmount record);
}