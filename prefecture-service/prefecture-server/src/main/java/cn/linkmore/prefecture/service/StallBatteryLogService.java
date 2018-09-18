package cn.linkmore.prefecture.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.prefecture.controller.staff.response.ResStaffStallBatteryLog;
import cn.linkmore.prefecture.response.ResStallBatteryLog;


/**
 * Service - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 */
public interface StallBatteryLogService {
	
	List<ResStallBatteryLog> findBatteryLogList(Long stallId);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ResStallBatteryLog sbl);

	/**
	 * @Description  查询管理版电池电量记录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffStallBatteryLog> findStaffBatteryLogList(Long stallId, HttpServletRequest request);
}
