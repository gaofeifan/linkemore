package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.UnusualLog;
/**
 * 异常日志写库mapper
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UnusualLogMasterMapper {
    /**
     * @Description  删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long logId);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(UnusualLog record);

    /**
     * @Description  新增(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(UnusualLog record);

    /**
     * @Description  更新(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(UnusualLog record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(UnusualLog record);
}