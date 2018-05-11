package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponSendRecord;

public interface CouponSendRecordMasterMapper {
	int delete(Long id);

    int save(CouponSendRecord record);

    int update(CouponSendRecord record);
}