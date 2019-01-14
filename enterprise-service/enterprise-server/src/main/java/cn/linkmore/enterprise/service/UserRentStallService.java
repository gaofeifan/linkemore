package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;

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

	Boolean owner(HttpServletRequest request);

}
