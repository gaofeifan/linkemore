package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.Qrc;
@Mapper
public interface QrcClusterMapper {

    Qrc findById(Long id);
}