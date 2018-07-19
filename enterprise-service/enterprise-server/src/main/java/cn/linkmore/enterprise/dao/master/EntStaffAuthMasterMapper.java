package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaffAuth;
/**
 * 企业用户权限---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffAuthMasterMapper {
    int deleteById(Long id);

    int save(EntStaffAuth record);

    int insertSelective(EntStaffAuth record);

    EntStaffAuth findById(Long id);

    int updateByIdSelective(EntStaffAuth record);

    int updateById(EntStaffAuth record);
}