package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.Orders;
@Mapper
public interface OrdersMasterMapper {
    int delete(Long id);

    int save(Orders record);

    int update(Orders record);
}