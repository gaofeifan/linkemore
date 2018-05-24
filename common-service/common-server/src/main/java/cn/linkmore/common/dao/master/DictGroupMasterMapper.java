package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.DictGroup;
/**
 * 数据字典分组(老) 写mapper
 * @author   GFF
 * @Date     2018年5月24日
 * @Version  v2.0
 */
@Mapper
public interface DictGroupMasterMapper {
    /**
     * @Description  根据id删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(DictGroup record);

    /**
     * @Description  新增(null处理)
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(DictGroup record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(DictGroup record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(DictGroup record);
}