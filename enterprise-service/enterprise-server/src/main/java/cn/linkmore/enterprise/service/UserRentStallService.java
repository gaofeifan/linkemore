package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.ResCurrentOwner;

/**
 * 用户长租车位
 * @author   GFF
 * @Date     2019年1月10日
 * @Version  v2.0
 */
public interface UserRentStallService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	OwnerRes findStall(HttpServletRequest request, ReqLocation location);

	/**
	 * @return 
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean control(ReqConStall reqConStall, HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Boolean owner(HttpServletRequest request);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResCurrentOwner current(HttpServletRequest request);

}
