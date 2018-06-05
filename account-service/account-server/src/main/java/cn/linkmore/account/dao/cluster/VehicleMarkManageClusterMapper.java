package cn.linkmore.account.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.response.ResVechicleMark;
/**
 * 车牌号管理(读)
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Mapper
public interface VehicleMarkManageClusterMapper {

    /**
     * @Description  根据id查询
     * @Author   GFF 
     * @Version  v2.0
     */
    ResVechicleMark findById(Long id);

	/**
	 * @Description  根据用户id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<VehicleMarkManage> findByUserId(Long userId);

	/**
	 * @Description  查询列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResVechicleMark> findResList(Long userId);

}