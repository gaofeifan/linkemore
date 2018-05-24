package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.DictGroup;

/**
 * 数据字典分组(老) 读mapper
 * 
 * @author GFF
 * @Date 2018年5月24日
 * @Version v2.0
 */
@Mapper
public interface DictGroupClusterMapper {

	/**
	 * @Description 根据id查询
	 * @Author GFF
	 * @Version v2.0
	 */
	DictGroup findById(Long id);
}