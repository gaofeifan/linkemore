package cn.linkmore.common.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.entity.WyAppVersion;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqWyAppVersion;
/**
 * 物业版版本mapper（写）
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface WyAppVersionMasterMapper {
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
    int insert(WyAppVersion record);

    /**
     * @Description   新增(null处理)  
     * @Author   GFF 
     * @Version  v2.0
     */
    int insertSelective(WyAppVersion record);

    /**
     * @Description  更新（null处理）
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(WyAppVersion record);

    /**
     * @Description  根据id更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(WyAppVersion record);

	/**
	 * @Description  新增通过app版本请求bean  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insertReq(ReqWyAppVersion version);

	/**
	 * @Description  更新通过app版本请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqWyAppVersion version);

	/**
	 * @Description  通过id删除(批量)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteAppById(List<Long> ids);
}