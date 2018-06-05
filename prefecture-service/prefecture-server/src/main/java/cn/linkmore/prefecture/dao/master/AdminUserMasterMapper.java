package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminUser;
@Mapper
public interface AdminUserMasterMapper {

    int save(AdminUser record);

    int update(AdminUser record);

	int delete(List<Long> ids);
}