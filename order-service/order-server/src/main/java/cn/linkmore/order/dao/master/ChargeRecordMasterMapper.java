package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.ChargeRecord;
@Mapper
public interface ChargeRecordMasterMapper {
    int delete(Integer id);

    int save(ChargeRecord record);

    int update(ChargeRecord record);
}