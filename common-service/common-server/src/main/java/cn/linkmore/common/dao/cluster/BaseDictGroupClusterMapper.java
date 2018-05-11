package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;
@Mapper
public interface BaseDictGroupClusterMapper {
    BaseDictGroup selectById(Long id);

}