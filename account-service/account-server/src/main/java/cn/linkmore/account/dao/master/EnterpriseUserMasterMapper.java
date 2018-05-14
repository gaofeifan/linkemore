package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.EnterpriseUser;
@Mapper
public interface EnterpriseUserMasterMapper {
    int deleteById(Long id);

    int insert(EnterpriseUser record);

    int insertSelective(EnterpriseUser record);

    EnterpriseUser selectById(Long id);

    int updateByIdSelective(EnterpriseUser record);

    int updateById(EnterpriseUser record);
}