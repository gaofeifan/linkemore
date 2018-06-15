package cn.linkmore.coupon.dao.master;

import java.util.Map;

import cn.linkmore.coupon.entity.BizSubject;

public interface BizSubjectMasterMapper {
    int delete(Long id);

    int save(BizSubject record);

    int update(BizSubject record);

	int startOrStop(Map<String, Object> param);
}