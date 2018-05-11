package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.CouponRollback;

public interface CouponRollbackMasterMapper {
    int delete(Long id);

    int save(CouponRollback record);

    int update(CouponRollback record);
}