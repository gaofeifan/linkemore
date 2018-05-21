package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.RechargeRecord;

public interface RechargeRecordMasterMapper {
    int delete(Integer id);

    int save(RechargeRecord record);

    int update(RechargeRecord record);
}