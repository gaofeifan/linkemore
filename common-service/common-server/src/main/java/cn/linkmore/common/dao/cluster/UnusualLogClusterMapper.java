package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UnusualLog;
/**
 * 异常日志查询mapper
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UnusualLogClusterMapper {


    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UnusualLog findById(Long logId);
}