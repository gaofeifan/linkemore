package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.User;
import cn.linkmore.account.response.ResUserDetails;
@Mapper
public interface UserClusterMapper {

    User selectById(Long id);

	List<ResUserDetails> selectResUserById(@Param("userId") Long userId);

	User selectByMobile(@Param("mobile")String mobile);

}