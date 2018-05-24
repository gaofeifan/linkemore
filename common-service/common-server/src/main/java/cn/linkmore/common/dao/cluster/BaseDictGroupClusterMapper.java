package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;
/**
 * 数据字典分组(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseDictGroupClusterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    BaseDictGroup findById(Long id);

}