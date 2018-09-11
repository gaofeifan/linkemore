package cn.linkmore.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		Boolean isOk = operations.setIfAbsent(key, newValue.toString());
		System.out.println("isOk------"+isOk+"key------"+key+"newValue------"+newValue);
        if(isOk) {
           // 获得锁
           return true;
        }else {
        	String alreadyValue =String.valueOf(operations.get(key));
        	System.out.println("alreadyValue------"+alreadyValue);
        	if(alreadyValue.equals( String.valueOf(newValue ))) {
        		return true;
        	}else {
        		return false;
        	}
        }
	}
	
	
}
