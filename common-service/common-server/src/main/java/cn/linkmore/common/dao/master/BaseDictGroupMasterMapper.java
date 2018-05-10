package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;

@Mapper
public interface BaseDictGroupMasterMapper {
    BaseDictGroup selectByPrimaryKey(Long id);

}