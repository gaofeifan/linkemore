package cn.linkmore.prefecture.dao.cluster;

import cn.linkmore.prefecture.entity.LockOperateLog;

public interface LockOperateLogClusterMapper {
    LockOperateLog findById(Long id);
}