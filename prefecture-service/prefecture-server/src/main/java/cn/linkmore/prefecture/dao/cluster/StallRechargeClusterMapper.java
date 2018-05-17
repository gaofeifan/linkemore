package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StallRecharge;
@Mapper
public interface StallRechargeClusterMapper {

    StallRecharge findById(Long id);
}