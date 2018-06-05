package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuthPre;
@Mapper
public interface AdminAuthPreMasterMapper {
    int delete(Long id);

    int save(AdminAuthPre record);

    int update(AdminAuthPre record);

	void deleteByAuthId(Long id);
}