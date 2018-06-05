package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.LockOperateLog;
/**
 * dao 锁操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LockOperateLogMasterMapper {
    int delete(List<Long> ids);

    int save(LockOperateLog record);

    int update(LockOperateLog record);

}