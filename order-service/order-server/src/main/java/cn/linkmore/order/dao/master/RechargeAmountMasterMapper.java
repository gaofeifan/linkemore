package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.RechargeAmount;
@Mapper
public interface RechargeAmountMasterMapper {
    int delete(Long id);

    int save(RechargeAmount record);

    int update(RechargeAmount record);
}