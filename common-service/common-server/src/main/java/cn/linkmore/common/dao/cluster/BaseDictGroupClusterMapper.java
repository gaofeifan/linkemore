package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;
/**
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
    BaseDictGroup selectById(Long id);

}