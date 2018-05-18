package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.OrderReceive;
@Mapper
public interface OrderReceiveClusterMapper {

    OrderReceive findById(Long id);

}