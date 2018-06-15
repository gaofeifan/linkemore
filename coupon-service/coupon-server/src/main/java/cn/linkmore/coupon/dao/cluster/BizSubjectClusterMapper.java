package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;
import cn.linkmore.coupon.response.ResBizSubjectBean;

public interface BizSubjectClusterMapper {

	ResBizSubjectBean findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResBizSubjectBean> findPage(Map<String, Object> param);

	List<ResBizSubjectBean> selectSubjectListByType(Long type);

	Integer check(Map<String, Object> param);
}