package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDict;
@Mapper
public interface BaseDictMasterMapper {
    int deleteById(Long id);

    int insert(BaseDict record);

    int insertSelective(BaseDict record);

    int updateByIdSelective(BaseDict record);

    int updateById(BaseDict record);
}