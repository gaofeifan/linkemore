package cn.linkmore.prefecture.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.response.ResPreGateway;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;

@Component
public class FreeStallInit{
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StallClusterMapper stallClusterMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper; 
	
	@Autowired
	private LockFactory lockFactory;
	
	@Scheduled(cron = "0 0/3 * * * ?")
	public void run() { 
		log.info("sync stall lock thread...");
		init(); 
	}
 
	public void init() { 
		log.info("free stall init ");
		List<Long> preIds = this.prefectureClusterMapper.findPreIdList();
		List<ResPreGateway> rpgs = this.prefectureClusterMapper.findPreGateList();
		ResponseMessage<LockBean> rm = null;
		List<LockBean> lbs = null;
		Map<String,LockBean> lbm = new HashMap<String,LockBean>();
		for(ResPreGateway rpg:rpgs) {
			rm =  this.lockFactory.findAvailableLock(rpg.getNumber());
			lbs = rm.getDataList(); 
			log.info(JsonUtil.toJson(rm));
			if(rm.getMsgCode()==200&&rm.getDataList()!=null) {
				for(LockBean lb:lbs) { 
					if(lb.getLockState().intValue()==LockStatus.UP.status){ 
						lbm.put(lb.getLockCode(), lb);
					} 
				}
			} 
		}
		
		log.info("preIds" + JSON.toJSON(preIds));
		List<Stall> list = this.stallClusterMapper.findByStatus(StallStatus.FREE.status);
		log.info("free stall list size " + list.size());
		Map<Long, Set<Object>> map = new HashMap<Long, Set<Object>>();
		Set<Object> sns = null; 
		for (Stall stall : list) {
			log.info("lbm.containsKey(stall.getLockSn()):{}",lbm.containsKey(stall.getLockSn()));
			log.info("this.redisService.exists(RedisKey.PREFECTURE_BUSY_STALL.key+stall.getLockSn()):{}",!this.redisService.exists(RedisKey.PREFECTURE_BUSY_STALL.key+stall.getLockSn()));
			if(lbm.containsKey(stall.getLockSn())&&!this.redisService.exists(RedisKey.PREFECTURE_BUSY_STALL.key+stall.getLockSn())){
				sns = map.get(stall.getPreId());
				if (sns == null) {
					sns = new HashSet<>();
					map.put(stall.getPreId(), sns);
				}
				sns.add(stall.getLockSn());
			} 
		}
		log.info("free stall map "+ map);
		Set<Long> keys = map.keySet();
		for (Long key : keys) {
			preIds.remove(key);
			redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key+key);
			redisService.addAll(RedisKey.PREFECTURE_FREE_STALL.key + key, map.get(key));
		}
		for(Long id:preIds) {
			log.info("redis remove key " + id);
			redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key+id);
		}
		Set<Object> bindLockSet = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key);
		for (Object bindLock : bindLockSet) {
			Map<String, Object> params = JSONObject.parseObject(bindLock.toString());
			if (params.get("preId") != null && params.get("lockSn") != null) {
				this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + params.get("preId").toString(),
						params.get("lockSn").toString());
			}
		}
	}

}
