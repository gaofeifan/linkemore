package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.TradeRecord;
@Mapper
public interface TradeRecordClusterMapper {

    TradeRecord findById(Long id);

}