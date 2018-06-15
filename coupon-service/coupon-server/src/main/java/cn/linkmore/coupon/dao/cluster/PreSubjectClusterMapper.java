package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.coupon.entity.PreSubject;
import cn.linkmore.coupon.response.ResPreSubjectBean;

public interface PreSubjectClusterMapper {

    PreSubject findById(Long id);

	Integer check(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResPreSubjectBean> findPage(Map<String, Object> param);
}