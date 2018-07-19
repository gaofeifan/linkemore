package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntPrefecture;
/**
 * 企业车区--写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntPrefectureMasterMapper {
    int deleteById(Long id);

    int save(EntPrefecture record);

    int saveSelective(EntPrefecture record);

    int updateByIdSelective(EntPrefecture record);

    int updateById(EntPrefecture record);
}