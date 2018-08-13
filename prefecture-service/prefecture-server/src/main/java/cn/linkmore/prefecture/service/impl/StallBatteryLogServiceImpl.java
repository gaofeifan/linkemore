package cn.linkmore.prefecture.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.prefecture.dao.cluster.StallBatteryLogClusterMapper;
import cn.linkmore.prefecture.dao.master.StallBatteryLogMasterMapper;
import cn.linkmore.prefecture.entity.StallBatteryLog;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import cn.linkmore.prefecture.service.StallBatteryLogService;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class StallBatteryLogServiceImpl implements StallBatteryLogService {
	@Autowired
	private StallBatteryLogClusterMapper batteryLogClusterMapper;
	@Autowired
	private StallBatteryLogMasterMapper stallBatteryLogMasterMapper;
	@Override 
	public List<ResStallBatteryLog> findBatteryLogList(Long stallId) {
		return this.batteryLogClusterMapper.findListByStallId(stallId);
	}

	@Override
	public void save(ResStallBatteryLog sbl) {
		StallBatteryLog log = ObjectUtils.copyObject(sbl, new StallBatteryLog());
		this.stallBatteryLogMasterMapper.save(log);
	}
	
	
}
