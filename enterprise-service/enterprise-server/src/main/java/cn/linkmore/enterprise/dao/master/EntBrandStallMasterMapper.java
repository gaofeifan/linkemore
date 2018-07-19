package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandStall;
@Mapper
public interface EntBrandStallMasterMapper {
    int delete(Long id);

    int save(EntBrandStall record);

    int update(EntBrandStall record);
}