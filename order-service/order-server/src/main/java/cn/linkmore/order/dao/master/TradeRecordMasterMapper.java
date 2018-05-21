package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.TradeRecord;

public interface TradeRecordMasterMapper {
    int delete(Long id);

    int save(TradeRecord record);

    int update(TradeRecord record);
}