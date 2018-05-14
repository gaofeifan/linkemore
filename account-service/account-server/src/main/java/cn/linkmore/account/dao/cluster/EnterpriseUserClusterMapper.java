package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.EnterpriseUser;
@Mapper
public interface EnterpriseUserClusterMapper {
    int deleteById(Long id);

    int insert(EnterpriseUser record);

    int insertSelective(EnterpriseUser record);

    EnterpriseUser selectById(Long id);

    int updateByIdSelective(EnterpriseUser record);

    int updateById(EnterpriseUser record);
}