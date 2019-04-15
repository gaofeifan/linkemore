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
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.prefecture.config.LockTools;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResPreGateway;
import cn.linkmore.redis.RedisService;

@Component
public class FreeStallInit {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StallClusterMapper stallClusterMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;

	@Autowired
	private LockTools lockTools;

	@Scheduled(cron = "0 0/3 * * * ?")
	public void run() {
		log.info("sync stall lock thread...");
		init();
	}

	/**
	 * 普通自营空闲车位处理
	 * 
	 * @param lbm
	 * @param list
	 */
	private void common(Map<String, ResLockInfo> lbm, List<Stall> list) {
		List<Long> preIds = this.prefectureClusterMapper.findPreIdList();
		Map<Long, Set<Object>> map = new HashMap<Long, Set<Object>>();
		Set<Object> sns = null;
		for (Stall stall : list) {
			if (lbm.containsKey(stall.getLockSn())
					&& !this.redisService.exists(RedisKey.PREFECTURE_BUSY_STALL.key + stall.getLockSn())) {
				sns = map.get(stall.getPreId());
				if (sns == null) {
					sns = new HashSet<>();
					map.put(stall.getPreId(), sns);
				}
				sns.add(stall.getLockSn());
			}
		}
		log.info("free stall map " + map);
		Set<Long> keys = map.keySet();
		for (Long key : keys) {
			preIds.remove(key);
			redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + key);
			redisService.addAll(RedisKey.PREFECTURE_FREE_STALL.key + key, map.get(key));
		} 
		for (Long id : preIds) {
			log.info("redis remove key " + id);
			redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + id);
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

	public void init() {
		log.info("free stall init... ");
		List<ResPreGateway> rpgs = this.prefectureClusterMapper.findPreGateList();
		List<ResLockInfo> lbs = null;
		Map<String, ResLockInfo> lbm = new HashMap<String, ResLockInfo>();
		for (ResPreGateway rpg : rpgs) {
			if(rpg.getNumber()!=null) { 
				lbs = this.lockTools.lockListByGroupCode(rpg.getNumber().trim());
				log.info("gateway = {}, preId= {} lock size = {}",rpg.getNumber(), rpg.getPreId(), lbs.size());
				if (lbs != null && lbs.size() != 0) {
					for (ResLockInfo lb : lbs) {
						if (lb.getLockState() == LockStatus.UP.status) {
							lbm.put(lb.getLockCode(), lb);
						}
					}
				}
			}
		}
		List<Stall> list = this.stallClusterMapper.findByStatus(StallStatus.FREE.status);
		try {
			this.common(lbm, list);
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackArray = e.getStackTrace();  
			for (int i = 0; i < stackArray.length; i++) {  
			    StackTraceElement element = stackArray[i];  
			    sb.append(element.toString() + "\n");   
			}   
			log.info("------------------------");
			log.info("micro service throw biz exception {}", sb.toString());
		}
	}

}
