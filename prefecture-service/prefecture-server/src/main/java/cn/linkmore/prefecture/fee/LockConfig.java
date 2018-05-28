package cn.linkmore.prefecture.fee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.linkmore.lock.bean.AbuttingBean;
import com.linkmore.lock.factory.LockFactory;
import cn.linkmore.redis.LockProperties;
/**
 * Config - lock配置
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class LockConfig { 
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LockProperties lockProperties;
	
	@Bean
	public LockFactory factoryConfig() {
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		log.info("huaqingurl------------------ {}", lockProperties.getHuaqingUrl());
		abuttingBean.setHuaQingUrl(lockProperties.getHuaqingUrl());
		abuttingBean.setLinkmoreUrl(lockProperties.getLinkmoreUrl());
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}
}
