package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.LockOperateLog;
/**
 * dao 锁操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LockOperateLogMasterMapper {
    int delete(Long id);

    int save(LockOperateLog record);

    int update(LockOperateLog record);
}