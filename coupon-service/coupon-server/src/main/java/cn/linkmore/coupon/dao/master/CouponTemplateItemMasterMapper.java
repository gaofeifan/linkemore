package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponTemplateItem;

public interface CouponTemplateItemMasterMapper {
	
	int delete(Long id);

    int save(CouponTemplateItem record);
    
    int update(CouponTemplateItem record);
}