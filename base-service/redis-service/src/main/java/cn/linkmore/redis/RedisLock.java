package cn.linkmore.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Service - Redis同步锁
 * @author changlei
 * @version 1.0
 *
 */
@Service 
public class RedisLock {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	private static final long LOCK_TIMEOUT = 60 * 1000;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/***
     * 加锁
     * @param key 锁
     * @param value 当前时间+超时时间
     * @return 获得锁返回true
     */
    public boolean lock(String key,String value){
    	//争抢锁
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){//setNX 返回boolean
            return true;
        }
        //当前key的值
        String currentValue =String.valueOf(redisTemplate.opsForValue().get(key));
        //拿到锁比对过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){
            //抢先更新超时时间并获取当前key的值
            String oldvalue  = String.valueOf(redisTemplate.opsForValue().getAndSet(key,value));
            if(!StringUtils.isEmpty(oldvalue)&&oldvalue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }
    /***
     * 解锁
     * @param key
     * @param value
     * @return
     */
    public void unlock(String key,String value){
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue)&&currentValue.equals(value)){
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
        	
        }
    }
    
    /***
     * 解锁
     * @param key
     * @param value
     * @return
     */
    public void unlock1(String key){
	        try {
	        	redisTemplate.opsForValue().getOperations().delete(key);
			} catch (Exception e) {
				
			}finally {
				redisTemplate.delete(key);
			}
        } 
    
	/**
	 * 队列放入唯一key 先进先出
	 * @param key 键
	 * @param 值
	 * @return 拿到锁
	 */
	public boolean  getLock(String key,Object newValue) {
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		Boolean isOk = operations.setIfAbsent(key, newValue.toString());
		log.info("......getLock isOk:{},key:{},newValue:{}",isOk,key,newValue);
        if(isOk) {
        	redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
           // 获得锁
           return true;
        }else {
        	String alreadyValue =(String)operations.get(key);
        	log.info("......getLock alreadyValue:{}",alreadyValue);
        	if(alreadyValue.equals(String.valueOf(newValue ))) {
        		redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        		return true;
        	}else {
        		return false;
        	}
        }
	}
}
