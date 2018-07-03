package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserStaff;
import cn.linkmore.account.request.ReqUserStaff;
/**
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface UserStaffMasterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int delete(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(UserStaff record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int update(UserStaff record);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqUserStaff record);

	/**
	 * @Description  修改
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqUserStaff record);
}