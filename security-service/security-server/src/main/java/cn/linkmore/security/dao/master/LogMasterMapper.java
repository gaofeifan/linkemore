package cn.linkmore.security.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Log;
/**
 * 日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LogMasterMapper {
    int delete(Long id);

    int save(Log record);

    int update(Log record);
}