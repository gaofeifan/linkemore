package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.AccountHistory;
@Mapper
public interface AccountHistoryMasterMapper {
    int delete(Long id);

    int save(AccountHistory record);

    int update(AccountHistory record);
}