package cn.linkmore.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.response.ResVechicleMark;

/**
 * 车牌号管理
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
public interface VehicleMarkManageService {

	/**
	 * @Description  根据用户查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResVechicleMark> selectResList(HttpServletRequest request);

	/**
	 * @param request 
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(cn.linkmore.user.request.ReqVehicleMark bean, HttpServletRequest request);

	/**
	 * @param request 
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteById(Long id, HttpServletRequest request);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVechicleMark selectById(Long id, HttpServletRequest request);

}
