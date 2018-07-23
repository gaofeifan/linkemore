/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResEntTypeStalls;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStallServiceImpl implements EntStallService {

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
	private LockFactory lockFactory;
	
	@Override
	public List<ResEntStalls> selectEntStalls(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("staffId", ru.getId());
		map.put("status", 1);
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
			
			for(int j = 0 ; i < preStalls; j++){
				ResStall resStall=stalls.get(j);
				if(resStall.getStatus() == StallStatus.USED.status){
					preUseStalls ++;
				}
			}
			
			for(int j = 0 ; i < preStalls; j++){
				ResStall resStall=stalls.get(j);
				//临停
				if(resStall.getType() == 1 ){
					preTempTypeStalls ++;
				}
				//临停使用
				if(resStall.getType() == 1 && resStall.getStatus() == StallStatus.USED.status){
					preTempUseTypeStalls ++;
				}
				//长租
				if(resStall.getType() == 2 ){
					preRentTypeStalls ++;
				}
				//长租使用
				if(resStall.getType() == 2 && resStall.getStatus() == StallStatus.USED.status){
					preRentUseTypeStalls ++;
				}
				//vip
				if(resStall.getType() == 3 ){
					preVipTypeStalls ++;
				}
				//vip使用
				if(resStall.getType() == 3 && resStall.getStatus() == StallStatus.USED.status){
					preVipUseTypeStalls ++;
				}
				
			}
			
			resEntStalls.setPreStalls(preStalls);
			resEntStalls.setPreUseStalls(preUseStalls);
			
			ResEntTypeStalls vipStalls = new ResEntTypeStalls();
			vipStalls.setPreTypeStalls(3);
			vipStalls.setTypeName("vip车位");
			vipStalls.setPreTypeStalls(preVipTypeStalls);
			vipStalls.setPreUseTypeStalls(preVipUseTypeStalls);
			typeSum.put("vip", vipStalls);
			ResEntTypeStalls rentStalls = new ResEntTypeStalls();
			rentStalls.setPreTypeStalls(2);
			rentStalls.setTypeName("长租车位");
			rentStalls.setPreTypeStalls(preRentTypeStalls);
			rentStalls.setPreUseTypeStalls(preRentUseTypeStalls);
			typeSum.put("rent", rentStalls);
			ResEntTypeStalls tempStalls = new ResEntTypeStalls();
			tempStalls.setPreTypeStalls(1);
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
		
		List<ResStall> stalls = this.stallClient.findPreStallList(stallIds);
		
		for(ResStall resStall:stalls){
			//临停
			if(resStall.getType() == 1){
				
			}
			//长租
			if(resStall.getType() == 2){
				
			}
			//vip
			if(resStall.getType() == 3){
				
			}
		}
		return stalls;
	}

	@Override
	public ResDetailStall selectEntDetailStalls(Long stallId) {
		ResStallEntity resStallEntity= this.stallClient.findById(stallId);
		if(StringUtil.isBlank(resStallEntity.getLockSn())){
			return null;
		}
		ResponseMessage<LockBean> res= lockFactory.getLockInfo(resStallEntity.getLockSn());
		LockBean lockBean=res.getData();
		ResDetailStall resDetailStall = new ResDetailStall();
		resDetailStall.setBetty(lockBean.getElectricity());
		resDetailStall.setSlaveCode(resStallEntity.getLockSn());
		resDetailStall.setStatus(lockBean.getLockState());
		return resDetailStall;
	}

	@Override
	public Map<String,Object> operatStalls(HttpServletRequest request, Long stallId, Integer state) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		
		//需要异步记录操作锁
		
		Map<String,Object> result = new HashMap<>();
		ResStallEntity resStallEntity= this.stallClient.findById(stallId);
		if(StringUtil.isBlank(resStallEntity.getLockSn())){
			return null;
		}
		//1 降下 2 升起
		if(state == 1){
			ResponseMessage<LockBean> res=lockFactory.lockDown(resStallEntity.getLockSn());
			if(res.isResult()){
				result.put("result", res.isResult());
				result.put("result", res.getMsg());
				return result ;
			}
		}else if(state == 2){
			ResponseMessage<LockBean> res=lockFactory.lockUp(resStallEntity.getLockSn());
			if(res.isResult()){
				result.put("result", res.isResult());
				result.put("result", res.getMsg());
				return result ;
			}
		}
		result.put("result", false);
		result.put("result", "操作失败");
		return result ;
	}

	@Override
	public Map<String, Object> change(HttpServletRequest request, Long stall_id, int changeStatus) {
		
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		
		Map<String, Object> map = new HashMap<>();
		int  result = 0;
		if(changeStatus == 1){
			List<Long> ids=new ArrayList<>();
			ids.add(stall_id);
			 result = this.stallClient.changedUp(ids);
		}else if(changeStatus == 2){
			result = this.stallClient.changedDown(stall_id);
		}
		if(result == 0){
			map.put("result",false);
			map.put("message","上线失败");
			return map;
		}
		map.put("result",false);
		map.put("message","上线成功");
		return map;
	}

	@Override
	public List<Long> findByStaffId(Long id) {
		return this.entStaffAuthClusterMapper.findByStaffId(id);
	}
	
	

}
