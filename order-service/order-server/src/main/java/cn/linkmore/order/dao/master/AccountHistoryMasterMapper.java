package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.AccountHistory;

public interface AccountHistoryMasterMapper {
    int delete(Long id);

    int save(AccountHistory record);

    int update(AccountHistory record);
}