package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.response.ResRollback;
import cn.linkmore.coupon.response.ResRollbackBean;
@Mapper
public interface RollbackClusterMapper {

    ResRollback findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResRollbackBean> findPage(Map<String, Object> param);

	List<ResRollback> findByTemplateIds(List<String> ids);

}