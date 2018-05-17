package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StallRecharge;
/**
 * dao 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallRechargeClusterMapper {

    StallRecharge findById(Long id);
}