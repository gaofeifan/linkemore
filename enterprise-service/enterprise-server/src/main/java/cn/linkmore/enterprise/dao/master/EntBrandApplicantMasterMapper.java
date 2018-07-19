package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandApplicant;
@Mapper
public interface EntBrandApplicantMasterMapper {
    int delete(Long id);

    int save(EntBrandApplicant record);

    int update(EntBrandApplicant record);
}