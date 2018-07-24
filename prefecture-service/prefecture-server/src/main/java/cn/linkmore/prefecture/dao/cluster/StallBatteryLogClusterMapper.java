package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallBatteryLog;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
/**
 * dao 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallBatteryLogClusterMapper {

    StallBatteryLog findById(Long id);
    
	List<ResStallBatteryLog> findListByStallId(Long stallId);
}