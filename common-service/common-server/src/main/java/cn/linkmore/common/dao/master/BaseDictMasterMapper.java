package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseDict;
/**
 * 数据字典mapper（写）
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseDictMasterMapper {
    int deleteById(Long id);

    int insert(BaseDict record);

    int insertSelective(BaseDict record);

    int updateByIdSelective(BaseDict record);

    int updateById(BaseDict record);
}