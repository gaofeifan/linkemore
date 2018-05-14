package cn.linkmore.prefecture.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.entity.Stall;

/**
 * 空闲锁池 采用redis 实现空闲锁的 生产和消费
 */
@Component
public class FreeLockPool {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private StallClusterMapper stallClusterMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * 定义空闲锁redis队列的KEY
	 */
	private final String FREE_LOKC_KEY = "freelock_key:";
	public static final String KEY_LOCK_ENABLE_CODE = "lock_enable_code";

	/**
	 * 初始化可用锁使用池 从数据库查询所有可用锁，缓存
	 */
	public void initPool() {
		List<Stall> stallList = stallClusterMapper.findByStatus(1);
		log.info(" cw: " + JSONObject.toJSONString(stallList));
		/**
		 * 取得锁服务中可用的锁
		 */
		Set<Object> enableLockSet = redisTemplate.opsForSet().members(KEY_LOCK_ENABLE_CODE);

		log.info(" cs: " + JSONObject.toJSONString(enableLockSet));
		/**
		 * 按专区归类
		 */
		Map<Long, Set<String>> enableStallMap = new HashMap<Long, Set<String>>();
		if (stallList != null) {
			for (Stall stall : stallList) {
				if (enableStallMap.get(stall.getPreId()) == null) {
					enableStallMap.put(stall.getPreId(), new HashSet<String>());
				}
				enableStallMap.get(stall.getPreId()).add(stall.getLockSn());
			}
		}
		// log.info(JSONObject.toJSONString(enableStallMap));
		/**
		 * 求交集
		 */
		Set<Long> keySet = enableStallMap.keySet();
		Iterator<Long> keyIt = keySet.iterator();
		while (keyIt.hasNext()) {
			Long key = keyIt.next();
			enableStallMap.get(key).retainAll(enableLockSet);
			log.info(key + " -- " + JSONObject.toJSONString(enableStallMap.get(key)));
			// 取得可用锁的并集
			Set<Object> enableLock = redisTemplate.opsForSet().members(FREE_LOKC_KEY + key);
			enableLock.removeAll(enableStallMap.get(key));
			// log.info(JSONObject.toJSONString(enableLock));
			if (enableLock.size() > 0) {
				redisTemplate.opsForSet().remove(FREE_LOKC_KEY + key, enableLock.toArray());
			}
			if (enableStallMap.get(key).size() > 0) {
				redisTemplate.opsForSet().add(FREE_LOKC_KEY + key, enableStallMap.get(key).toArray());
			}
			log.info("可用锁列" + key);
			log.info(JSONObject.toJSONString(redisTemplate.opsForSet().members(FREE_LOKC_KEY + key)));

		}
	}

	/**
	 * 加入空闲锁至 空闲锁池中
	 */
	public void addFreeLock(Long pid, String lockSn) {
		redisTemplate.opsForSet().add(FREE_LOKC_KEY + pid, lockSn);
	}

	/**
	 * 从空闲锁池中消费一个锁
	 */
	public String getFreeLock(Long pid) {
		// 该专区所有可用锁
		return (String) redisTemplate.opsForSet().pop(FREE_LOKC_KEY + pid);
	}

	/**
	 * 获取专业可用锁个数
	 * 
	 * @param pid
	 * @return
	 */
	public Long freeStallCount(Long pid) {
		return redisTemplate.opsForSet().size(FREE_LOKC_KEY + pid);
	}

	/**
	 * 指定车位锁操作
	 * 
	 * @param type
	 * @param key
	 * @param val
	 * @return
	 */
	public void redisSetOper(int type, String key, String val) {
		if (type == 0) {
			redisTemplate.opsForSet().add(key, val);
		} else if (type == 1) {
			redisTemplate.opsForSet().remove(key, val);
		}
	}

	/**
	 * 获取指定车位集
	 * 
	 * @param type
	 * @param key
	 * @param val
	 */
	public Set<Object> redisSetOper(String key) {
		return redisTemplate.opsForSet().members(key);
	}

}
