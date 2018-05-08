package cn.linkmore.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.request.ReqRedisObject;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;

@RestController
@RequestMapping("/common/redis")
public class RedisController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "{key}", method = RequestMethod.GET)
	public Object get(@PathVariable("key") String key) {
		Object value = this.redisService.get(key);
		log.info("get redis with key:{} value:{}", key, JsonUtil.toJson(value));
		return value;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody ReqRedisObject redisObject) {
		log.info("add redis object : {}", JsonUtil.toJson(redisObject));
		if (redisObject.getExpireTime() != null) {
			this.redisService.set(redisObject.getKey(), redisObject.getValue(), redisObject.getExpireTime());
		} else {
			this.redisService.set(redisObject.getKey(), redisObject.getValue());
		}
	}

	@RequestMapping(value = "{key}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("key") String key) {
		log.info("delete redis object with key:{}",key);
		this.redisService.remove(key);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody String[] keys) {
		log.info("delete redis object with keys:{}",JsonUtil.toJson(keys));
		this.redisService.remove(keys);
	}
	
	
}
