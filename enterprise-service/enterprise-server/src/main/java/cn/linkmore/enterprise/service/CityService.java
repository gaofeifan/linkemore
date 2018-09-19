package cn.linkmore.enterprise.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.common.response.ResCity;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
public interface CityService {

	/**
	 * @Description  查询管理版用户城市
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<cn.linkmore.enterprise.controller.staff.response.ResCity> findStaffCity(HttpServletRequest request);

}
