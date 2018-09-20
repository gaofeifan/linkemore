package cn.linkmore.common.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.StaffAppVersion;
import cn.linkmore.common.request.ReqStaffAppVersion;
/**
 * 管理版版本mapper（写）
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface StaffAppVersionMasterMapper {
    /**
     * @Description 根据id删除 
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description 新增  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insert(StaffAppVersion record);

    /**
     * @Description   新增(null处理)  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(StaffAppVersion record);

    /**
     * @Description  更新（null处理）
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(StaffAppVersion record);

    /**
     * @Description  根据id更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(StaffAppVersion record);

	/**
	 * @Description  新增通过app版本请求bean  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insertReq(ReqStaffAppVersion version);

	/**
	 * @Description  更新通过app版本请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqStaffAppVersion version);

	/**
	 * @Description  通过id删除(批量)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteAppById(List<Long> ids);
}