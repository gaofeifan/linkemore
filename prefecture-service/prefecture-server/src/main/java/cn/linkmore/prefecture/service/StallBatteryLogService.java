package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.prefecture.response.ResStallBatteryLog;


/**
 * Service - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 */
public interface StallBatteryLogService {
	
	List<ResStallBatteryLog> findBatteryLogList(Long stallId);
}
