package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehMarkIdAndUserId;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;

/**
 * 车牌号管理
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface VehicleMarkManageService {

	/**
	 * @Description  根据用户id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<VehicleMarkManage> findByUserId(Long id);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqVehicleMark bean);

	/**
	 * @Description  根据id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteById(ReqVehMarkIdAndUserId v);

	
	/**
	 * @Description  根据userid查询响应数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResVechicleMark> findResList(Long userId);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVechicleMark findById(Long id);

}
