/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.controller.ent.request.ReqStallExcCause;
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResEntTypeStalls;
import cn.linkmore.enterprise.controller.ent.response.ResStallName;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffClusterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.entity.StallExcStatus;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.enterprise.service.StallExcStatusService;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.prefecture.client.StallBatteryLogClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StallOperateLogClient;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStallServiceImpl implements EntStallService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private static final String DOWN_CAUSE = "cause_down";
	
	@Autowired
	private StallBatteryLogClient stallBatteryLogClient;
	@Autowired
	private StallOperateLogClient stallOperateLogClient;
	@Autowired
	private BaseDictClient dictClient;
	@Autowired
	private StallExcStatusService stallExcStatusService;
	@Autowired
	private EntStaffAuthClusterMapper  entStaffAuthClusterMapper;
	
	@Autowired
	private EntAuthPreClusterMapper entAuthPreClusterMapper;
	
	@Autowired
	private EntPrefectureClusterMapper entPrefectureClusterMapper;
	
	@Autowired
	private EntAuthStallClusterMapper entAuthStallClusterMapper;

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private StallClient stallClient;
	
	@Autowired
	private EntOrderClient orderClient;
	
	@Autowired
	private LockFactory lockFactory;

	@Autowired
	private EntRentUserService entRentUserService;	

	@Autowired
	private EntStaffClusterMapper entStaffClusterMapper;
	
	public static final String DOWN = "down-";
	@Override
	public List<ResEntStalls> selectEntStalls(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("staffId", ru.getId());
		List<EntStaffAuth> entStaffAuths= entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		if(size == 0){
			return new ArrayList<ResEntStalls>();
		}
		
		EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String,Object> param = new HashMap<>();
		param.put("authId", entStaffAuth.getAuthId());
		List<EntAuthPre> entAuthPres= entAuthPreClusterMapper.findList(param);
		int preSize = entAuthPres.size();
		EntAuthPre entAuthPre = null;
//		List<EntRentUser> rentUsers = entRentUserService.findAll();
		
		List<ResEntStalls> entStallList = new ArrayList<>();
		ResEntStalls resEntStalls = null;
		Map<String,Object> params = null;
		List<ResStall> stalls = null;
		EntPrefecture entPrefeture = null;
		for(int i = 0; i < preSize ; i ++){
			entAuthPre = entAuthPres.get(i);
			if(entAuthPre == null){
				continue;
			}
			entPrefeture = entPrefectureClusterMapper.findByPreId(entAuthPre.getPreId());
			if(entPrefeture == null){
				continue;
			}
			resEntStalls = new ResEntStalls();
			resEntStalls.setPreId(entAuthPre.getPreId());
			resEntStalls.setPreName(entPrefeture.getPreName());
			
			params = new HashMap<String,Object>();
			params.put("preId", entAuthPre.getPreId());
			stalls = stallClient.findStallList(params);
			int preStalls = stalls.size();
			int preUseStalls = 0;
			int preVipTypeStalls = 0;
			int preVipUseTypeStalls = 0;
			int preRentTypeStalls = 0;
			int preRentUseTypeStalls = 0;
			int preTempTypeStalls = 0;
			int preTempUseTypeStalls = 0;
			
			Map<String,ResEntTypeStalls> typeSum = new HashMap<>();
			
			for(int j = 0 ; j < preStalls; j++){
				ResStall resStall=stalls.get(j);
				if(resStall.getStatus() == StallStatus.USED.status){
					preUseStalls ++;
				}
			}
			
			for(int j = 0 ; j < preStalls; j++){
				ResStall resStall=stalls.get(j);
				//临停使用 || 临停
				if(resStall.getType() == 1 && resStall.getStatus() == StallStatus.USED.status){
					preTempUseTypeStalls ++;
				}else if(resStall.getType() == 1 ){
					preTempTypeStalls ++;
				}
				//长租使用||长租
				if(resStall.getType() == 2 && resStall.getStatus() == StallStatus.USED.status){
					preRentUseTypeStalls ++;
				}else if(resStall.getType() == 2 ){
					preRentTypeStalls ++;
				}
					/*StringBuilder sb = new StringBuilder();
					for (EntRentUser rentUser : rentUsers) {
						if(rentUser.getStallId().equals(resStall.getId())) {
							sb.append(rentUser.getPlate()).append("/");
						}
					}*/
				//vip使用  ||vip
				if(resStall.getType() == 3 && resStall.getStatus() == StallStatus.USED.status){
					preVipUseTypeStalls ++;
				}else if(resStall.getType() == 3 ){
					preVipTypeStalls ++;
				}
				
			}
			
			resEntStalls.setPreStalls(preStalls);
			resEntStalls.setPreUseStalls(preUseStalls);
			
			ResEntTypeStalls vipStalls = new ResEntTypeStalls();
			vipStalls.setType((short) 3);
			vipStalls.setTypeName("vip车位");
			vipStalls.setPreTypeStalls(preVipTypeStalls);
			vipStalls.setPreUseTypeStalls(preVipUseTypeStalls);
			typeSum.put("vip", vipStalls);
			ResEntTypeStalls rentStalls = new ResEntTypeStalls();
			rentStalls.setType((short) 2);
			rentStalls.setTypeName("长租车位");
			rentStalls.setPreTypeStalls(preRentTypeStalls);
			rentStalls.setPreUseTypeStalls(preRentUseTypeStalls);
			typeSum.put("rent", rentStalls);
			ResEntTypeStalls tempStalls = new ResEntTypeStalls();
			tempStalls.setType((short) 1);
			tempStalls.setTypeName("临停车位");
			tempStalls.setPreTypeStalls(preTempTypeStalls);
			tempStalls.setPreUseTypeStalls(preTempUseTypeStalls);
			typeSum.put("temp", tempStalls);
			resEntStalls.setTypeStalls(typeSum);
			entStallList.add(resEntStalls);
		}
		return entStallList;
	}

	@Override
	public List<ResStallName> selectStalls(HttpServletRequest request,Long preId, Short type) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("staffId", ru.getId());
		map.put("status", 1);
		List<EntStaffAuth> entStaffAuths= entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		List<ResStallName> stallNames = new ArrayList<>();
		if(size == 0){
			return new ArrayList<ResStallName>();
		}
//		EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String, Object> param = new HashMap<String,Object>();
		List<Long> list = entStaffAuths.stream().map(ent -> ent.getAuthId()).collect(Collectors.toList());
		param.put("authIds", list);
//		param.put("authId", entStaffAuth.getAuthId());
//		List<Long> stallIds= entAuthStallClusterMapper.findStallList(param);
		List<Long> stallIds= entAuthStallClusterMapper.findStallListByIds(param);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("list", stallIds);
		List<ResStall> stalls = this.stallClient.findPreStallList(params);  //根据stallid 集合查询车位
		List<ResOrderPlate> orders= orderClient.findPlateByPreId(preId);
		List<Long> collect = stalls.stream().map(stall -> stall.getId()).collect(Collectors.toList());
		List<StallExcStatus> stallExcList = null;
		if(collect != null && collect.size() > 0) {
			stallExcList = this.stallExcStatusService.findExcStatusList(collect);
		}
		
		//设置车位对应的车牌号
		List<cn.linkmore.enterprise.controller.ent.response.ResStall> stallList = new ArrayList<>();
		cn.linkmore.enterprise.controller.ent.response.ResStall stall = null;
//		lockFactory.
		for(ResStall resStall:stalls){
			if(orders == null ){ 
				break;
			}
			if(resStall.getPreId().equals(preId)) {
				stall = ObjectUtils.copyObject(resStall, new cn.linkmore.enterprise.controller.ent.response.ResStall());
				stall.setStallId(resStall.getId());
				for(ResOrderPlate orderPlate : orders){
					if(resStall.getId() == orderPlate.getStallId()){
						stall.setPlateNo(orderPlate.getPlateNo());
						break;
					}
				}	
				//	设置车位锁异常状态
				for (StallExcStatus stallExcStatus : stallExcList) {
					if(stallExcStatus.getStallId() == resStall.getId()) {
						stall.setExcStatus(false);
						break;
					}
				}
				stallList.add(stall);
			}
		}
		ResStallName stallName = null;
		StringBuilder sb = new StringBuilder();
		int end = 0;
		stallName = new ResStallName();
		stallName.setId("0");
		cn.linkmore.enterprise.controller.ent.response.ResStall resStall = null;
		for (int i = 0; i < stallList.size(); i++) {
			resStall = stallList.get(i);
			resStall.setpId(stallName.getId());
			stallName.getStalls().add(resStall);
			if(i == end) {
				sb.append(stallList.get(i).getStallName()).append("-");
				end += 5;
			}else if(i == (end-1)) {
				sb.append(stallList.get(i).getStallName());
				stallName.setStallName(sb.toString());
				Integer id = Integer.parseInt(stallName.getId());
				sb = new StringBuilder();
				stallNames.add(stallName);
				stallName = new ResStallName();
				stallName.setId(id+1+"");
			}else if(i == stallList.size()-1) {
				sb.append(stallList.get(i).getStallName());
				stallName.setStallName(sb.toString());
				stallNames.add(stallName);
			}
		}
		return stallNames;
	}

	@Override
	public ResDetailStall selectEntDetailStalls(Long stallId,HttpServletRequest request) {
		if(!checkAuth(stallId, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStallEntity resStallEntity= this.stallClient.findById(stallId);
		if(StringUtil.isBlank(resStallEntity.getLockSn())){
			return null;
		}
		ResponseMessage<LockBean> res= lockFactory.getLockInfo(resStallEntity.getLockSn());
		LockBean lockBean=res.getData();
		ResDetailStall resDetailStall = new ResDetailStall();
		resDetailStall.setSlaveCode(resStallEntity.getLockSn());
		if(lockBean == null){
			return resDetailStall;
		}
		ResEntOrder resEntOrder = this.orderClient.findOrderByStallId(resStallEntity.getId());
		List<EntRentUser> rentUsers = this.entRentUserService.findAll();
		if(resStallEntity.getType() != null && resStallEntity.getType() == 2) {
			StringBuilder sb = new StringBuilder();
			StringBuilder mobiles = new StringBuilder();
			for (EntRentUser entRentUser : rentUsers) {
				if(entRentUser.getStallId().equals(resStallEntity.getId())) {
					sb.append(entRentUser.getPlate()).append("/");
					mobiles.append(entRentUser.getMobile()).append("/");
				}
			}
			resDetailStall.setPlate(sb.length() != 0 ? sb.substring(0, sb.length()-1):null);
			resDetailStall.setMobile(mobiles.length() != 0 ? mobiles.substring(0, mobiles.length()-1): null);
			if(resStallEntity.getStatus() == 2) {
				resDetailStall.setDownTime(resEntOrder.getLockDownTime());
			}
				
			
		}else if(resStallEntity.getType() != null && resStallEntity.getType() == 1) {
			if(resStallEntity.getStatus() == 2) {
				resDetailStall.setDownTime(resEntOrder.getLockDownTime());
				resDetailStall.setOrderNo(resEntOrder.getOrderNo());
				resDetailStall.setStartTime(resEntOrder.getBeginTime());
				String duration = DateUtils.getDuration(new Date(), resEntOrder.getBeginTime());
				resDetailStall.setStartDate(duration);
			}
		}
		
		resDetailStall.setBetty(lockBean.getElectricity());
		resDetailStall.setStatus(lockBean.getLockState());
		resDetailStall.setStallId(resStallEntity.getId());
		return resDetailStall;
	}

	@Override
	public Map<String,Object> operatStalls(HttpServletRequest request, Long stallId, Integer state) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(!checkAuth(stallId, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		//需要异步记录操作锁
		Map<String,Object> result = new HashMap<>();
		ResStallEntity resStallEntity= this.stallClient.findById(stallId);
		if(StringUtil.isBlank(resStallEntity.getLockSn())){
			result.put("result", false);
			result.put("result", "操作失败");
			return result;
		}
		ResponseMessage<LockBean> res = null;
//		result.put("result", res.isResult());
//		result.put("result", res.getMsg());
		
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	//1 降下 2 升起
				if(state == 1){
					lockFactory.lockDown(resStallEntity.getLockSn());
				}else if(state == 2){
					lockFactory.lockUp(resStallEntity.getLockSn());
				}
	        }
	    }).start();
		
		return result ;
	}

	
	@Override
	public void change(HttpServletRequest request, Long stallId, int i, Long remarkId, String remark) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		ReqStallOperateLog sol = new ReqStallOperateLog();
		sol.setCreateTime(new Date());
		sol.setOperation((short)i);
		sol.setOperatorId(ru.getId());
		sol.setSource((short)2);
		sol.setStallId(stallId);
		sol.setRemarkId(remarkId);	
		sol.setRemark(remark);
		sol.setStatus(1);
		this.stallOperateLogClient.save(sol);
		this.change(request, stallId, i);
		if ((remarkId != null) && (remarkId.longValue() > 0L)) {
			ResBaseDict dict = this.dictClient.find(remarkId);
			if(dict == null) {
				throw new BusinessException(StatusEnum.DOWN_CAUSE_EXISTS);
			}
			if ("battery-change".equals(dict.getCode())) {
				try {
//					LockServerClient lockServerClient = ThriftClientFactory.getThriftClient(this.rpcConfig.getHost(),
//							this.rpcConfig.getPort(), this.rpcConfig.getTimeout());
//					String count = ParkingLockClient.getUpOrDownCounts(lockServerClient, stall.getLockSn());
//					this.log.info("offline change battery {}", lockInfo.get);
					ResStallBatteryLog sbl = new ResStallBatteryLog();
					sbl.setAdminId(ru.getId());
					sbl.setAdminName(ru.getMobile());
					sbl.setCreateTime(new Date());
//					sbl.setTotalNum(Integer.valueOf(count));
					sbl.setVoltage(0d);
					sbl.setStallId(stallId);
					this.stallBatteryLogClient.save(sbl);
				} catch (Exception e) {
					e.printStackTrace();
//					throw new BusinessException(StatusEnum.STALL_LOCK_OFFLINE);
				}
			}
		}
	}

	@Override
	public Map<String, Object> change(HttpServletRequest request, Long stall_id, int changeStatus) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(!checkAuth(stall_id, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("stallId", stall_id);
		param.put("status", 1);
		this.stallExcStatusService.updateExcStatus(param);
		
		Map<String, Object> map = new HashMap<>();
		int  result = 0;
		if(changeStatus == 1){
			List<Long> ids=new ArrayList<>();
			ids.add(stall_id);
			 result = this.stallClient.changedUp(ids);
		}else if(changeStatus == 2){
			result = this.stallClient.changedDown(stall_id);
		}
		/*if(result == 0){
			map.put("result",false);
			map.put("message","操作失败");
			return map;
		}*/
		map.put("result",false);
		map.put("message","操作成功");
		return map;
	}

	@Override
	public List<Long> findStaffId(Map<String, Long> map) {
		return this.entStaffAuthClusterMapper.findByStaffId(map);
	}

	@Override
	public void reset(Long stallId,HttpServletRequest request) {
		if(checkAuth(stallId, request)) {
			Map<String, Object> map = new HashMap<>();
			map.put("stallId", stallId);
			map.put("status", 1);
			this.stallExcStatusService.updateExcStatus(map);
		}else {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		
	}

	@Override
	public List<ResBaseDict> downCause() {
		return this.dictClient.findList(DOWN_CAUSE);
	}
	
	public boolean checkAuth(Long stallId,HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		Map<String, Object> map = new HashMap<>();
		map.put("stallId", stallId);
		map.put("staffId", ru.getId());
		Integer flag = this.entAuthStallClusterMapper.checkAuth(map);
		if(flag > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void saveStallExcCause(List<ReqStallExcCause> causes) {
		List<String> list = new ArrayList<>();
		for (ReqStallExcCause reqStallExcCause : causes) {
			list.add(DOWN+reqStallExcCause.getReportSource());
		}
		List<ResBaseDict> dicts = this.dictClient.findListByCodes(list);
		List<StallExcStatus> excs = new ArrayList<>();
		List<ResStall> stalls = this.stallClient.findStallList(new HashMap<String, Object>());
		StallExcStatus excStall = new StallExcStatus();
		for (ReqStallExcCause reqStallExcCause : causes) {
			excStall.setCreateTime(new Date());
			excStall.setStatus((short)0);
			if(dicts != null && dicts.size() != 0) {
				for (ResBaseDict resBaseDict : dicts) {
					if((DOWN+reqStallExcCause.getReportSource()).equals(resBaseDict.getCode())) {
						excStall.setExcRemark(resBaseDict.getName());
						excStall.setExcStatus(resBaseDict.getId());
					}
				}
				for (ResStall resStallOps : stalls) {
					if(reqStallExcCause.getStallLock().equals(resStallOps.getLockSn())) {
						excStall.setStallId(resStallOps.getId());
					}
				}
				if(excStall.getExcStatus() != null && excStall.getStallId() != null) {
					excs.add(excStall);
				}
			}
		}
		if(excs.size() != 0) {
			this.stallExcStatusService.saveBatch(excs);
		}
	}
	
	
}
