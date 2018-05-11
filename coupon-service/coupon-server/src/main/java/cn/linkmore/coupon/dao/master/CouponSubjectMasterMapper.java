package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponSubject;

public interface CouponSubjectMasterMapper {
	int delete(Long id);

    int save(CouponSubject record);

    int update(CouponSubject record);
}