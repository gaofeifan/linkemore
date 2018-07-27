package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandStall;
@Mapper
public interface EntBrandStallMasterMapper {
    int delete(List<Long> ids);

    int save(EntBrandStall record);

    int update(EntBrandStall record);
}