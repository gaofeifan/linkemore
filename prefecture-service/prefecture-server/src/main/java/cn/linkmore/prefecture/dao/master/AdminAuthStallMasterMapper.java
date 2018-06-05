package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuthStall;
@Mapper
public interface AdminAuthStallMasterMapper {
    int delete(Long id);

    int save(AdminAuthStall record);

    int update(AdminAuthStall record);

	void deleteByAuthId(Long id);
}