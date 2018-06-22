package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserGuide;
/**
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Mapper
public interface UserGuideClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    UserGuide findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserGuide> findAll();

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int count(Map<String, Object> param);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserGuide> findList(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserGuide> findPage(Map<String, Object> param);


}