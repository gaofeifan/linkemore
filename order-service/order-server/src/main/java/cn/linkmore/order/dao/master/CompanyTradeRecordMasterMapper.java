package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.CompanyTradeRecord;

public interface CompanyTradeRecordMasterMapper {
    int delete(Integer id);

    int save(CompanyTradeRecord record);
    
    int update(CompanyTradeRecord record);
}