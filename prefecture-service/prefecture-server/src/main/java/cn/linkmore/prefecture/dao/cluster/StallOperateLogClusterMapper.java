package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallOperateLog;
/**
 * dao 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallOperateLogClusterMapper {
    StallOperateLog findById(Long id);
}