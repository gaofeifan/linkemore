package cn.linkmore.enterprise.service;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount;

/**
 * 车场运营实现
 * @author   GFF
 * @Date     2018年7月21日
 * @Version  v2.0
 */
public interface PrefectureService {

	/**
	 * @return 
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */

	List<ResPreOrderCount> findPreList(HttpServletRequest request);

	
}
