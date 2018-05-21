package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.TradeRecord;
@Mapper
public interface TradeRecordMasterMapper {
    int delete(Long id);

    int save(TradeRecord record);

    int update(TradeRecord record);
}