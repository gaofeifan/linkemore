package cn.linkmore.coupon.dao.cluster;

import cn.linkmore.coupon.entity.CouponTemplate;

public interface CouponTemplateClusterMapper {
	/**
	 * 根据主键查询优惠券套餐
	 * @param id
	 * @return
	 */
    CouponTemplate findById(Long id);

}