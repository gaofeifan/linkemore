package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.EnterpriseUser;
@Mapper
public interface EnterpriseUserClusterMapper {

    EnterpriseUser selectById(Long id);

	EnterpriseUser selectByUserId(@Param("userId")Long userId);

}