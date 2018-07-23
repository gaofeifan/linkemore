package cn.linkmore.enterprise.dao.cluster;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.EntBrandUser;
/**
 * 授权品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandUserClusterMapper {
	
	/**
	 * 根据企业id和用户id查看是否为授权用户
	 * @param entId
	 * @param userId
	 * @return
	 */
	Integer findBrandUser(Long entId, Long userId);

    EntBrandUser findById(Long id);

	Integer check(Map<String, Object> param);

}