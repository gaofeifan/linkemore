/**
 * 
 */
package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.entity.EntAuthStall;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
public interface EntStallService {

	/**
	 * 查询车场信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	List<ResEntStalls> selectEntStalls(Long id);

	/**
	 * @Description  根据员工查询可操作车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Long> findByStaffId(Long id);

}
