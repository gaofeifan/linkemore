package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Theme;
@Mapper
public interface ThemeClusterMapper {

    Theme findById(Long id);

}