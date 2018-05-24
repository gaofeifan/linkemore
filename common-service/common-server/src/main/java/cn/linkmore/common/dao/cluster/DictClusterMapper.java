package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.Dict;
/**
 * 数据字典(老) 读mapper
 * @author   GFF
 * @Date     2018年5月24日
 * @Version  v2.0
 */
@Mapper
public interface DictClusterMapper {
	
	/**
	 * @Description 根据id查询
	 * @Author GFF
	 * @Version v2.0
	 */
	Dict findById(Long id);
}