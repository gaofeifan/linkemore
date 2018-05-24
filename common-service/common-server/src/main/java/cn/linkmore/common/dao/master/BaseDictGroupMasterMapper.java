package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDictGroup;
/**
 * 数据字典分组mapper(写)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseDictGroupMasterMapper {
    int deleteById(Long id);

    int insert(BaseDictGroup record);

    int insertSelective(BaseDictGroup record);

    int updateByIdSelective(BaseDictGroup record);

    int updateById(BaseDictGroup record);
}