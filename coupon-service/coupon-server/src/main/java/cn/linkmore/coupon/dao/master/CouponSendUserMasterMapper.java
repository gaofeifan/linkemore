package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponSendUser;

public interface CouponSendUserMasterMapper {
	int delete(Long id);

    int save(CouponSendUser record);

    int update(CouponSendUser record);
}