package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntOperateAuth;
/**
 * 操作权限--写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntOperateAuthMasterMapper {
    int deleteById(Long id);

    int save(EntOperateAuth record);

    int saveSelective(EntOperateAuth record);

    int updateByIdSelective(EntOperateAuth record);

    int updateById(EntOperateAuth record);
}