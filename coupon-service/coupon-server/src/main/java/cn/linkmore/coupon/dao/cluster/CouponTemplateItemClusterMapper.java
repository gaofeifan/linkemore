package cn.linkmore.coupon.dao.cluster;

import cn.linkmore.coupon.entity.CouponTemplateItem;

public interface CouponTemplateItemClusterMapper {
    int delete(Long id);

    int save(CouponTemplateItem record);

    int update(CouponTemplateItem record);
}