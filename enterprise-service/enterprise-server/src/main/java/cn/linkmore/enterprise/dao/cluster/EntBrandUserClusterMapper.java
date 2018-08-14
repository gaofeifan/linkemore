package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.response.ResBrandUser;
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
	Integer findBrandUser(Map<String, Object> map);

    ResBrandUser findById(Long id);

	Integer check(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResBrandUser> findPage(Map<String, Object> param);

	/**
	 * 查询用户对应的企业ID
	 * @param userId
	 * @return
	 */
	List<Long> findUserEntList(Long userId);

	Integer checkExist(Map<String, Object> param);

}