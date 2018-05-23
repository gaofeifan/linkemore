package cn.linkmore.account.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.User;
import cn.linkmore.account.response.ResUserDetails;
/**
 * 用户mapper(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    User selectById(Long id);

	/**
	 * @Description  查询resuser
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUserDetails> selectResUserById(@Param("userId") Long userId);

	/**
	 * @Description  根据手机号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	User selectByMobile(@Param("mobile")String mobile);

}