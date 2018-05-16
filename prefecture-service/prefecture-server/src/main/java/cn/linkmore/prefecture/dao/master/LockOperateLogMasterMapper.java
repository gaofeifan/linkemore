package cn.linkmore.prefecture.dao.master;

import cn.linkmore.prefecture.entity.LockOperateLog;

public interface LockOperateLogMasterMapper {
    int delete(Long id);

    int save(LockOperateLog record);

    int update(LockOperateLog record);
}