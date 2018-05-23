package cn.linkmore.account.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.User;
/**
 * 用户(写)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserMasterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(User record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(User record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(User record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(User record);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateByColumn(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateLoginTime(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateMobile(Map<String, Object> param);

}