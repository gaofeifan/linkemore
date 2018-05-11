package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponStrategy;

public interface CouponStrategyMasterMapper {
	int delete(Long id);

    int save(CouponStrategy record);

    int update(CouponStrategy record);
}