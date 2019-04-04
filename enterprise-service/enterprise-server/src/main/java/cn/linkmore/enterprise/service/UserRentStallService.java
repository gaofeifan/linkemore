package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.request.ReqUserRentStall;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.ResCurrentOwner;
import cn.linkmore.enterprise.controller.app.response.ResParkingRecord;
import cn.linkmore.enterprise.controller.app.response.ResRentUser;

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
	Boolean control(ReqUserRentStall reqConStall, HttpServletRequest request);

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
	/**
	 * 查询用户是否有长租车位授权标识
	 * @param request
	 * @return
	 */
	Boolean authFlag(HttpServletRequest request);

	List<ResRentUser> findStallList(HttpServletRequest request, ReqLocation location);

	Boolean controlAuth(ReqUserRentStall reqConStall, HttpServletRequest request);

	List<ResParkingRecord> parkingRecord(HttpServletRequest request, Integer pageNo);

	List<OwnerPre> authStall(HttpServletRequest request);

}
