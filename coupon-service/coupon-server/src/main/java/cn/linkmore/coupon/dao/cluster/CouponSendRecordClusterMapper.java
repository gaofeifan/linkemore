package cn.linkmore.coupon.dao.cluster;

import cn.linkmore.coupon.entity.CouponSendRecord;

public interface CouponSendRecordClusterMapper {
	int delete(Long id);

    int save(CouponSendRecord record);

    CouponSendRecord findById(Long id);

    int update(CouponSendRecord record);
}