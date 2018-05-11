package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponQrcEnterprise;

public interface CouponQrcEnterpriseMasterMapper {
    int delete(Long id);

    int save(CouponQrcEnterprise record);

    int update(CouponQrcEnterprise record);
}