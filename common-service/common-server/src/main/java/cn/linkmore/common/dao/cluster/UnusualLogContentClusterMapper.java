package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UnusualLogContent;
/**
 * 异常日志内容查询mapper
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UnusualLogContentClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    UnusualLogContent findById(Long logId);
}