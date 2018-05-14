package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.EnterpriseUser;
import feign.Param;
@Mapper
public interface EnterpriseUserClusterMapper {

    EnterpriseUser selectById(Long id);

	List<EnterpriseUser> selectByUserId(@Param("userId")Long userId);

}