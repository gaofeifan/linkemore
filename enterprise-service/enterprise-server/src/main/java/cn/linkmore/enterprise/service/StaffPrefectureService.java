package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;


/**
 * 
 * @author   changlei
 * @Date     2018年9月10日
 * @Version  v2.0
 */
public interface StaffPrefectureService {

	void  control(SraffReqConStall reqOperatStall,HttpServletRequest request);
	
}
