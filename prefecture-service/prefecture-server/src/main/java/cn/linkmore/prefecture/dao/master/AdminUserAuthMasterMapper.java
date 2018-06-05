package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminUserAuth;
@Mapper
public interface AdminUserAuthMasterMapper {
    int delete(Long id);

    int save(AdminUserAuth record);

    int update(AdminUserAuth record);

	void deleteByUserId(Long id);

	void deleteByAuthId(List<Long> ids);
}