package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AppVersion;

@Mapper
public interface AppVersionClusterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppVersion record);

    AppVersion selectByPrimaryKey(Long id);

    int update(AppVersion record);
    
    AppVersion current(Integer appType);
}