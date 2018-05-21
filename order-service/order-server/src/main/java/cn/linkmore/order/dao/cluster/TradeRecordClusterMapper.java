package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.TradeRecord;

public interface TradeRecordClusterMapper {

    TradeRecord findById(Long id);

}