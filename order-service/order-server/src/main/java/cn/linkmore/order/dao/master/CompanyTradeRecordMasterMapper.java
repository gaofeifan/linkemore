package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.CompanyTradeRecord;
@Mapper
public interface CompanyTradeRecordMasterMapper {
    int delete(Integer id);

    int save(CompanyTradeRecord record);
    
    int update(CompanyTradeRecord record);
}