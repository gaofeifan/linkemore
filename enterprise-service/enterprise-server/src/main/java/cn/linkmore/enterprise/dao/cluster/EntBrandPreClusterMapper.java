package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.EntBrandPre;

/**
 * 品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandPreClusterMapper {

    EntBrandPre findById(Long id);

	Integer check(Map<String, Object> param);

}