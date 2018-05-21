package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.ChargeRecord;

public interface ChargeRecordMasterMapper {
    int delete(Integer id);

    int save(ChargeRecord record);

    int update(ChargeRecord record);
}