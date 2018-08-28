/**
 * 
 */
package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.controller.ent.request.ReqStallExcCause;
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResStallName;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallBatteryLog;

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
	List<ResEntStalls> selectEntStalls(HttpServletRequest request);

	/**
	 * @Description  根据员工查询可操作车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Long> findStaffId(Map<String, Long> map);

	/** 查询车位列表信息
	 * @author luzhishen
	 * @param string 
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	List<ResStallName> selectStalls(HttpServletRequest request,Long preId, Short type, String string);

	/**
	 * 查询车位详细信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	ResDetailStall selectEntDetailStalls(Long stallId,HttpServletRequest request);

	/**
	 * 
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	Map<String, Object> operatStalls(HttpServletRequest request, Long stallId, Integer state);

	/**
	 * 车位上下线（changeStatus：1上线 2 下线）
	 * 
	 * @author luzhishen
	 * @Date 2018年7月21日
	 * @Version v1.0
	 */
	Map<String, Object> change(HttpServletRequest request, Long stall_id, int changeStatus, Long remarkId, String remark);

	/**
	 * @param request 
	 * @Description  复位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void reset(Long stallId, HttpServletRequest request);

	/**
	 * 查询下线原因
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResBaseDict> downCause();

	/**
	 * @Description  保存车位异常原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveStallExcCause(List<ReqStallExcCause> causes);

	/**
	 * @Description  查询电池更换记录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStallBatteryLog> findLockChangeRecord(Long stallId);


}
