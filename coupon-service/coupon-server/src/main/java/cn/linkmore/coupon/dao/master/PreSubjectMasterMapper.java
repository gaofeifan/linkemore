package cn.linkmore.coupon.dao.master;

import cn.linkmore.coupon.entity.PreSubject;

public interface PreSubjectMasterMapper {
    int delete(Long id);

    int save(PreSubject record);

    int update(PreSubject record);
}