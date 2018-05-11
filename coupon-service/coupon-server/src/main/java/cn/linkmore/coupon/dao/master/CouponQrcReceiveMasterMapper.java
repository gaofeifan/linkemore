package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponQrcReceive;

public interface CouponQrcReceiveMasterMapper {
    int delete(Long id);

    int save(CouponQrcReceive record);

    int update(CouponQrcReceive record);
}