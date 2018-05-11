package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDict;
@Mapper
public interface BaseDictClusterMapper {

    BaseDict selectById(Long id);

}