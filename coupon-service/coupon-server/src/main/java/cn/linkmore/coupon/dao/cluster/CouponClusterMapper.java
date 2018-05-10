package cn.linkmore.coupon.dao.cluster;

import cn.linkmore.coupon.entity.Coupon;

public interface CouponClusterMapper {
	/**
	 * 根据主键查询优惠券信息	 * @param id Long
	 * @return
	 */
    Coupon findById(Long id);

}