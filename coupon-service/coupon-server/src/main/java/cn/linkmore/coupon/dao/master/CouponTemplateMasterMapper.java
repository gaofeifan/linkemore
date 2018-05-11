package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponTemplate;

public interface CouponTemplateMasterMapper {
    
    int delete(Long id);

    int save(CouponTemplate record);
    
    int update(CouponTemplate record);
}