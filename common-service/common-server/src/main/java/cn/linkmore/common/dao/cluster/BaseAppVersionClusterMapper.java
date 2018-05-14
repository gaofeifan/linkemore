package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseAppVersion;
@Mapper
public interface BaseAppVersionClusterMapper {


    BaseAppVersion selectById(Long id);

}