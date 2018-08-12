package cn.linkmore.prefecture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.linkmore.lock.bean.AbuttingBean;
import com.linkmore.lock.factory.LockFactory;
/**
 * Config - lock配置
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class LockConfig { 
	
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LockProperties lockProperties;
	
	@Bean
	public LockFactory factoryConfig() {
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean(); 
		abuttingBean.setHuaQingUrl(lockProperties.getHuaqingUrl());
		abuttingBean.setLinkmoreUrl(lockProperties.getLinkmoreUrl());
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}
}
