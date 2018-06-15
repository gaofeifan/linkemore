package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.response.ResStrategy;
@Mapper
public interface StrategyClusterMapper {

    ResStrategy findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResStrategy> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);

}