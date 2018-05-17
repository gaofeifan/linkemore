package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.LockOperateLog;
/**
 * dao 锁操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LockOperateLogClusterMapper {
    LockOperateLog findById(Long id);
}