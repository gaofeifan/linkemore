package cn.linkmore.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

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
	
	/***
     * 加锁
     * @param key 锁
     * @param value 当前时间+超时时间
     * @return 获得锁返回true
     */
    public boolean lock(String key,String value){
    	//争抢锁
        if(stringRedisTemplate.opsForValue().setIfAbsent(key,value)){//setNX 返回boolean
            return true;
        }
        //当前key的值
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //拿到锁比对过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){
            //抢先更新超时时间并获取当前key的值
            String oldvalue  = stringRedisTemplate.opsForValue().getAndSet(key,value);
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
    
	/**
	 * 队列放入唯一key 先进先出
	 * @param key 键
	 * @param 值
	 * @return 拿到锁
	 */
	public boolean  getLock(String key,Object newValue) {
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		Boolean isOk = operations.setIfAbsent(key, newValue.toString());

		System.out.println("isOk------"+isOk+"key------"+key+"newValue------"+newValue);
        if(isOk) {
        	redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
           // 获得锁
           return true;
        }else {
        	System.out.println(operations.get(key));
        	String alreadyValue =(String)operations.get(key);
        	System.out.println("alreadyValue------"+alreadyValue);
        	if(alreadyValue.equals(String.valueOf(newValue ))) {
        		redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        		return true;
        	}else {
        		return false;
        	}
        }
	}
	
	
}
