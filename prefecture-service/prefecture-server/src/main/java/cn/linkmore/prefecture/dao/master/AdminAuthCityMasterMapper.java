package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuthCity;
@Mapper
public interface AdminAuthCityMasterMapper {
    int delete(Long id);

    int save(AdminAuthCity record);

    int update(AdminAuthCity record);

	void deleteByAuthId(Long id);
}