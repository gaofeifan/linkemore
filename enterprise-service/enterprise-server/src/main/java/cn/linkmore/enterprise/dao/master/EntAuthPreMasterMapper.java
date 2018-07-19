package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntAuthPre;
/**
 * 权限专区---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntAuthPreMasterMapper {
    int deleteById(Long id);

    int save(EntAuthPre record);

    int saveSelective(EntAuthPre record);

    int updateByIdSelective(EntAuthPre record);

    int updateById(EntAuthPre record);
}