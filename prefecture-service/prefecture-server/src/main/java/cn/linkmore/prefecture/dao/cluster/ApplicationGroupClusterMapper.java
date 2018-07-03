package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.ApplicationGroup;
import cn.linkmore.prefecture.response.ResApplicationGroup;
/**
 * 应用程序mapper  --- 读
 * @author   GFF
 * @Date     2018年6月23日
 * @Version  v2.0
 */
@Mapper
public interface ApplicationGroupClusterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    ApplicationGroup findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResApplicationGroup> findPage(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);
}