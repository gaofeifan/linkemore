package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandAd;
@Mapper
public interface EntBrandAdMasterMapper {
    int delete(Long id);

    int save(EntBrandAd record);

    int update(EntBrandAd record);
}