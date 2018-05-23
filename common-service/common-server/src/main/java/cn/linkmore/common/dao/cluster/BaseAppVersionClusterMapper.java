package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseAppVersion;
/**
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseAppVersionClusterMapper {


    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    BaseAppVersion selectById(Long id);

}