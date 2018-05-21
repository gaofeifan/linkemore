package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.Account;
@Mapper
public interface AccountClusterMapper {

    Account findById(Long id);

}