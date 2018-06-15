package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.ValuePack;

public interface ValuePackMasterMapper {
    int delete(Long id);

    int save(ValuePack record);

    int update(ValuePack record);
}