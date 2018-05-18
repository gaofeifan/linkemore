package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallBatteryLog;
/**
 * dao 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallBatteryLogMasterMapper {
    int delete(Long id);

    int save(StallBatteryLog record);

    int update(StallBatteryLog record);
}