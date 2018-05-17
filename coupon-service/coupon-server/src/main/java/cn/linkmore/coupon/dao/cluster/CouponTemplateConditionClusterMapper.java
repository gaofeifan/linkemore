package cn.linkmore.coupon.dao.cluster;

import java.util.List;

import cn.linkmore.coupon.entity.CouponTemplateCondition;
 
public interface CouponTemplateConditionClusterMapper {
	
	CouponTemplateCondition findById(Long id);
	
	List<CouponTemplateCondition> findConditionList(List<Long> ids);
}