package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandApplicant;
/**
 * 品牌申请人
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandApplicantClusterMapper {

    EntBrandApplicant findById(Long id);

	Integer check(Map<String, Object> param);

}