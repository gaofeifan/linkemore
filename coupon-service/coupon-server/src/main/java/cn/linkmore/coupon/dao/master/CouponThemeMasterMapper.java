package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponTheme;

public interface CouponThemeMasterMapper {
    int delete(Long id);

    int save(CouponTheme record);

    int update(CouponTheme record);

}