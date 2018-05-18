package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.TemplateItem;
@Mapper
public interface TemplateItemMasterMapper {
	
	int delete(Long id);

    int save(TemplateItem record);
    
    int update(TemplateItem record);
}