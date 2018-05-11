package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponTemplateCondition;

public interface CouponTemplateConditionMasterMapper {
	int delete(Long id);

	int save(CouponTemplateCondition record);

	int update(CouponTemplateCondition record);
}