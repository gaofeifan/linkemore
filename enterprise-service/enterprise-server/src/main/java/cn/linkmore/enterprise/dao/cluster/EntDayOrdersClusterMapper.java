package cn.linkmore.enterprise.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntDayOrders;
/**
 * 车区日订单--读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntDayOrdersClusterMapper {
    EntDayOrders findById(Long id);

}