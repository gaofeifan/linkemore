/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResEntTypeStalls;
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
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStallServiceImpl implements EntStallService {

	private static final String DOWN_CAUSE = "cause_down";
	
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
	public List<ResStall> selectStalls(HttpServletRequest request,Long preId, Short type) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("staffId", ru.getId());
		map.put("status", 1);
		List<EntStaffAuth> entStaffAuths= entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		if(size == 0){
			return new ArrayList<ResStall>();
		}
		EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("authId", entStaffAuth.getAuthId());
		List<Long> stallIds= entAuthStallClusterMapper.findStallList(param);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("list", stallIds);
		List<ResStall> stalls = this.stallClient.findPreStallList(params);
		List<ResOrderPlate> orders= orderClient.findPlateByPreId(preId);
		List<Long> collect = stalls.stream().map(stall -> stall.getId()).collect(Collectors.toList());
		List<StallExcStatus> stallExcList = this.stallExcStatusService.findExcStatusList(collect);
		//设置车位对应的车牌号
		for(ResStall resStall:stalls){
			if(orders == null ){ 
				break;
			}
			for(ResOrderPlate orderPlate : orders){
				if(resStall.getId() == orderPlate.getStallId()){
					resStall.setPlateNo(orderPlate.getPlateNo());
					break;
				}
			}
			//	设置车位锁异常状态
			for (StallExcStatus stallExcStatus : stallExcList) {
				if(stallExcStatus.getStallId() == resStall.getId()) {
					resStall.setExcStatus(false);
					break;
				}
			}
		}
		return stalls;
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
		//1 降下 2 升起
		if(state == 1){
			res=lockFactory.lockDown(resStallEntity.getLockSn());
		}else if(state == 2){
			res=lockFactory.lockUp(resStallEntity.getLockSn());
		}
		/*if(res == null){
			result.put("result", false);
			result.put("result", "操作失败");
			return result ;
		}*/
		result.put("result", res.isResult());
		result.put("result", res.getMsg());
		return result ;
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
	
	

}
