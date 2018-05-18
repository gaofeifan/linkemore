package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Template;
@Mapper
public interface TemplateMasterMapper {
    
    int delete(Long id);

    int save(Template record);
    
    int update(Template record);
}