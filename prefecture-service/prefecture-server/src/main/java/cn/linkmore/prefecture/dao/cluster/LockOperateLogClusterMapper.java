package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.LockOperateLog;
@Mapper
public interface LockOperateLogClusterMapper {
    LockOperateLog findById(Long id);
}