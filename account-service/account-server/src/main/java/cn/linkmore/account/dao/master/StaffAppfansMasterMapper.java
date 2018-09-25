package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.StaffAppfans;
import cn.linkmore.account.entity.UserAppfans;
/**
 * 管理版用户微信mapper(写)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface StaffAppfansMasterMapper {
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
    int insert(StaffAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(StaffAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(StaffAppfans record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(StaffAppfans record);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatusByUserId(@Param("userId") Long userId,@Param("status") Integer status);

	/**
	 * @Description  通过用户id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteByUserId(Long userId);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateFansUserId(Long id);

}