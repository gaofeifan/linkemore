package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallBatteryLog;
/**
 * dao 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallBatteryLogClusterMapper {

    StallBatteryLog findById(Long id);
}