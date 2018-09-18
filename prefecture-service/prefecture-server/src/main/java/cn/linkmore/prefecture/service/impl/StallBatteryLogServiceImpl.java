package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallBatteryLog;
import cn.linkmore.prefecture.dao.cluster.StallBatteryLogClusterMapper;
import cn.linkmore.prefecture.dao.master.StallBatteryLogMasterMapper;
import cn.linkmore.prefecture.entity.StallBatteryLog;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import cn.linkmore.prefecture.service.AdminUserService;
import cn.linkmore.prefecture.service.StallBatteryLogService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
/**
 * Service实现类 - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class StallBatteryLogServiceImpl implements StallBatteryLogService {
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private StallBatteryLogClusterMapper batteryLogClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserClient userClient;
	@Autowired
	private StallBatteryLogMasterMapper stallBatteryLogMasterMapper;
	@Override 
	public List<ResStallBatteryLog> findBatteryLogList(Long stallId) {
		List<ResStallBatteryLog> list = this.batteryLogClusterMapper.findListByStallId(stallId);
		return list;
	}
	@Override
	public void save(ResStallBatteryLog sbl) {
		StallBatteryLog log = ObjectUtils.copyObject(sbl, new StallBatteryLog());
		this.stallBatteryLogMasterMapper.save(log);
	}

	@Override
	public List<ResStaffStallBatteryLog> findStaffBatteryLogList(Long stallId,HttpServletRequest request) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		List<ResStaffStallBatteryLog> logs = new ArrayList<>();
		List<ResStallBatteryLog> list = this.batteryLogClusterMapper.findListByStallId(stallId);
		ResStaffStallBatteryLog log;
		List<ResAdminUser> users = this.adminUserService.findAll();
		if(cu != null) {
			for (ResStallBatteryLog resStallBatteryLog : list) {
				log = ObjectUtils.copyObject(resStallBatteryLog, new ResStaffStallBatteryLog());
				for (ResAdminUser resAdminUser : users) {
					if(resAdminUser.getId().equals(cu.getId())) {
						log.setAdminName(resAdminUser.getRealname());
					}
				}
				logs.add(log);
			}
		}
		return logs;
	}
	
	
}
