package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponQrc;

public interface CouponQrcMasterMapper {
    int delete(Long id);

    int save(CouponQrc record);

    int update(CouponQrc record);
}