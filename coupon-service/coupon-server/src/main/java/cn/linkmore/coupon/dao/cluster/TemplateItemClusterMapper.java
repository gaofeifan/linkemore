package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateItem;
@Mapper
public interface TemplateItemClusterMapper {
    int delete(Long id);

    int save(TemplateItem record);

    int update(TemplateItem record);
}