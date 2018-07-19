package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandAd;
/**
 * 品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandAdClusterMapper {

    EntBrandAd findById(Long id);

	Integer check(Map<String, Object> param);
    
}