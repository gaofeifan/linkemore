package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.RechargeRecord;
@Mapper
public interface RechargeRecordMasterMapper {
    int delete(Integer id);

    int save(RechargeRecord record);

    int update(RechargeRecord record);
}