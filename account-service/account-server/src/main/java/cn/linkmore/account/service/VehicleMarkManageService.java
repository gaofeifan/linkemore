package cn.linkmore.account.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.account.entity.VehicleMarkManage;
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
	 * @Description  根据userid查询响应数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResVechicleMark> findResList(HttpServletRequest request);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVechicleMark findById(Long id);

	/**
	 * @return 
	 * @Description  新增--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean save(cn.linkmore.account.controller.app.request.ReqVehicleMark bean, HttpServletRequest request);

	/**
	 * @Description  删除--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteById(Long id, HttpServletRequest request);

	/**
	 * @Description  查询用户车牌--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
//	List<ResVechicleMark> selectResList(HttpServletRequest request);
	
	/**
	 * @Description  根据用户id和车牌id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVechicleMark findByPlateNo(Map<String,Object> param);

	int update(ReqVehicleMark bean);

	List<ResVechicleMark> findResList(Long userId);

	int insertByNoRepeat(ReqVehicleMark bean);

}
