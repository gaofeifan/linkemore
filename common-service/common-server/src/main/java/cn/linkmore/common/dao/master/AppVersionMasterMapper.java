package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AppVersion;

@Mapper
public interface AppVersionMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppVersion record);

    AppVersion selectByPrimaryKey(Long id);

    int update(AppVersion record);
    
    AppVersion current(Integer appType);
}