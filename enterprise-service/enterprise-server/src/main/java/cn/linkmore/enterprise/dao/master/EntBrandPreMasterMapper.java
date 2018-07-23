package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandPre;
@Mapper
public interface EntBrandPreMasterMapper {
    int delete(Long id);

    int save(EntBrandPre record);

    int update(EntBrandPre record);
}