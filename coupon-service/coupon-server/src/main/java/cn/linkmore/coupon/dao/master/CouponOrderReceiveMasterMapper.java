package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponOrderReceive;

public interface CouponOrderReceiveMasterMapper {
    int delete(Long id);

    int save(CouponOrderReceive record);

    int update(CouponOrderReceive record);
}