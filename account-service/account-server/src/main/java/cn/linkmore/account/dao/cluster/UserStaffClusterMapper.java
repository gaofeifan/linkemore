package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserStaff;
import cn.linkmore.account.response.ResUserStaff;
/**
 * 
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserStaffClusterMapper {
    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    ResUserStaff findById(Long id);
    
    /**
     * 根据手机号获取
     * @param mobile
     * @return
     */
    ResUserStaff findByMobile(String mobile);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(Map<String, Object> param);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<UserStaff> findPage(Map<String, Object> param);
}