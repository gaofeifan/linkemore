package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.Combo;

public interface ComboMasterMapper {
    int delete(Long id);

    int save(Combo record);

    int update(Combo record);
}