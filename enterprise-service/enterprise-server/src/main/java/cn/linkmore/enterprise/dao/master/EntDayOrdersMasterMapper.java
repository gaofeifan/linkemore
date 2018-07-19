package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntDayOrders;
/**
 * 车区日订单--写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntDayOrdersMasterMapper {
    int deleteById(Long id);

    int save(EntDayOrders record);

    int saveSelective(EntDayOrders record);

    int updateByIdSelective(EntDayOrders record);

    int updateById(EntDayOrders record);
}