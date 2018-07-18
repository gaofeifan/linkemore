package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandStall;

/**
 * 品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandStallClusterMapper {

    EntBrandStall findById(Long id);

	Integer check(Map<String, Object> param);

}