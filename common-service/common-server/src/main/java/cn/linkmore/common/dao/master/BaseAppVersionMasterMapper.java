package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseAppVersion;
/**
 * app版本mapper（写）
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface BaseAppVersionMasterMapper {
    int deleteById(Long id);

    int insert(BaseAppVersion record);

    int insertSelective(BaseAppVersion record);

    int updateByIdSelective(BaseAppVersion record);

    int updateById(BaseAppVersion record);
}