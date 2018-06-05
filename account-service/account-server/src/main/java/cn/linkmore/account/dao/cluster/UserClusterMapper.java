package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
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
    ResUser findById(Long id);

	/**
	 * @Description  查询resuser
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUserDetails> findResUserById( Long userId);

	/**
	 * @Description  根据手机号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUser findByMobile(String mobile);

	/**
	 * @Description  根据条件查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  根据条件分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPageUser> findPage(Map<String, Object> param);

}