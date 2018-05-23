package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.UserAppfans;
/**
 * 用户微信mapper(写)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserAppfansMasterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(String id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(UserAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(UserAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(UserAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(UserAppfans record);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatusByUserId(@Param("userId") Long userId,@Param("status") Integer status);

}