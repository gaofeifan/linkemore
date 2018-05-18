package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.Rollback;
@Mapper
public interface RollbackClusterMapper {

    Rollback findById(Long id);

}