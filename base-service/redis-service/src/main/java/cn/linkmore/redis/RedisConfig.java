package cn.linkmore.redis;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;
/**
 * Config - Redis配置
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
@EnableCaching
@EnableRedisHttpSession
public class RedisConfig extends CachingConfigurerSupport {  
	
	@Autowired
	private RedisProperties redisProperties;
	
	
	@Bean
	public JedisPoolConfig redisPoolConfig(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(redisProperties.getPoolMaxActive());
        poolConfig.setMaxIdle(redisProperties.getPoolMaxIdle());
        poolConfig.setMinIdle(redisProperties.getPoolMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getPoolMaxWait());
        poolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
        poolConfig.setTestOnCreate(redisProperties.isTestOnCreate());
        poolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
        return poolConfig;
	}
	
	/**
	 * 获取redis 主从配置
	 * */
	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration(){ 
		/**
		 * 主从配置信息
		 * */
		try{  
			RedisSentinelConfiguration redisSentinelConfiguration  = new RedisSentinelConfiguration();
			redisSentinelConfiguration.setMaster(redisProperties.getSentinelMaster());
			RedisNode redisNode = null; 
			for(String node: redisProperties.getSentinelNodes().split(" ")){  
				redisNode = new RedisNode(node.split(":")[0],Integer.valueOf(node.split(":")[1]));
				redisSentinelConfiguration.addSentinel(redisNode);
			} 
			return redisSentinelConfiguration;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	 
    @Override
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        return redisCacheManager;
    }

    /**
     * 自定义生成redis-key
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName()).append(".");
                sb.append(method.getName()).append(".");
                for (Object obj : objects) {
                    sb.append(obj.toString());
                } 
                return sb.toString();
            }
        };
    }
	
	@Autowired 
	private RedisSentinelConfiguration redisSentinelConfiguration;
	
	@Autowired 
	private JedisPoolConfig redisPoolConfig;
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory(){
		JedisConnectionFactory jedisConnectionFactory = null; 
		jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,redisPoolConfig);
		jedisConnectionFactory.setHostName(redisProperties.getHost());
		jedisConnectionFactory.setPort(redisProperties.getPort());
		jedisConnectionFactory.setPassword(redisProperties.getPassword());
		jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
		jedisConnectionFactory.setTimeout(redisProperties.getTimeout()); 
		return jedisConnectionFactory;
	}
	
	@Autowired 
	private JedisConnectionFactory redisConnectionFactory;
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){ 
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	
	
	@Value("${server.session.timeout}")
    private int sessionTimeout = 60;
 
    @Primary
    @Bean
    public RedisOperationsSessionRepository sessionRepository(
        @Qualifier("sessionRedisTemplate") RedisOperations<Object, Object> sessionRedisTemplate,
        ApplicationEventPublisher applicationEventPublisher) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
        sessionRepository.setDefaultMaxInactiveInterval(sessionTimeout);
        return sessionRepository;
    }

	
}
