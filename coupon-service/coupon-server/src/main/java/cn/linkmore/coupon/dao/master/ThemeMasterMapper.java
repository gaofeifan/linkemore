package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Theme;
@Mapper
public interface ThemeMasterMapper {
    int delete(Long id);

    int save(Theme record);

    int update(Theme record);

}