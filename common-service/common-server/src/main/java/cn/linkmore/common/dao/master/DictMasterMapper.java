package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.Dict;
/**
 * 数据字典(老) 读mapper
 * @author   GFF
 * @Date     2018年5月24日
 * @Version  v2.0
 */
@Mapper
public interface DictMasterMapper {
    /**
     * @Description	通过id删除  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(Dict record);

    /**
     * @Description  新增(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(Dict record);

    /**
     * @Description  更新(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(Dict record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(Dict record);
}