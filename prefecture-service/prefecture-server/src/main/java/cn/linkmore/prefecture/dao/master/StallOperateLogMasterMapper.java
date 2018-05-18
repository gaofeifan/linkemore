package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallOperateLog;
/**
 * dao 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallOperateLogMasterMapper {
    int delete(Long id);

    int save(StallOperateLog record);

    int update(StallOperateLog record);
}