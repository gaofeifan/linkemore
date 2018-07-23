/**
 * 
 */
package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;
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
	 * @Description  根据员工查询可操作车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Long> findByStaffId(Long id);
	/** 查询车位列表信息
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

	/**
	 * 
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	Map<String, Object> operatStalls(Long staffId, Long stallId, Integer state);

	/**
	 * 车位上下线（changeStatus：1上线 2 下线）
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	Map<String, Object> change(Long id, Long stall_id, int changeStatus);

}
