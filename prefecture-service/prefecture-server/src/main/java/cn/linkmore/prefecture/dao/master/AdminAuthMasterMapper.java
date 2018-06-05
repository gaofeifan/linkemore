package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminAuth;
@Mapper
public interface AdminAuthMasterMapper {
    int delete(List<Long> ids);

    int save(AdminAuth record);

    int update(AdminAuth record);
}