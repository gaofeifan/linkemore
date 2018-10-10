package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.CustomerInfo;

@Mapper
public interface CustomerInfoMapper {
    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);
}