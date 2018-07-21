/**
 * 
 */
package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
public interface EntStallService {

	/**
	 * 通过员工ID查询车场信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	List<ResEntStalls> selectEntStalls(Long id);

}
