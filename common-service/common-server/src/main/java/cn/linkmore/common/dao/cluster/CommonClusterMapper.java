package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.Common;
/**
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface CommonClusterMapper {
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public List<Map<String,Object>> findList(Common common);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Integer count(Common common);


}