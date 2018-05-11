package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;
@Mapper
public interface BaseDictGroupMasterMapper {
    int deleteById(Long id);

    int insert(BaseDictGroup record);

    int insertSelective(BaseDictGroup record);

    int updateByIdSelective(BaseDictGroup record);

    int updateById(BaseDictGroup record);
}