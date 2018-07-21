/**
 * 
 */
package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.prefecture.response.ResStall;

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

	/**
	 * 查询车位列表信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	List<ResStall> selectStalls(Long staffId,Long preId, Short type);

	/**
	 * 查询车位详细信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	ResDetailStall selectEntDetailStalls(Long stallId);

}
