package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.Account;
@Mapper
public interface AccountMasterMapper {
    int delete(Long id);

    int save(Account record);

    int update(Account record);
}