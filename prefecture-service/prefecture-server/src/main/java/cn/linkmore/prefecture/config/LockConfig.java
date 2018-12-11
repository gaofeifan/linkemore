package cn.linkmore.prefecture.config;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * Config - lock配置
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class LockConfig { 
		
	@Autowired
	private LockProperties lockProperties;
	
	private static final Logger log = LoggerFactory.getLogger(LockConfig.class);
	
/*	@Bean
	public LockFactory factoryConfig() {
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		log.info("linkmore-new-url = {}",lockProperties.getLinkmoreNewUrl());
		abuttingBean.setLinkmoreUrl(lockProperties.getLinkmoreUrl());
		abuttingBean.setLinkmoreNewUrl(lockProperties.getLinkmoreNewUrl());
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}*/
	
	
	
}


