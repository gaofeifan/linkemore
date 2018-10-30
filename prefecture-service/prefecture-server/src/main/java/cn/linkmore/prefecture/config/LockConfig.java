package cn.linkmore.prefecture.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.linkmore.lock.bean.AbuttingBean;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
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
	
	@Bean
	public LockFactory factoryConfig() {
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		log.info("linkmore-new-url = {}",lockProperties.getLinkmoreNewUrl());
		abuttingBean.setLinkmoreUrl(lockProperties.getLinkmoreUrl());
		abuttingBean.setLinkmoreNewUrl(lockProperties.getLinkmoreNewUrl());
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}
	
	public static void main(String[] args) {
		
		LockFactory lockFactory =   LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		abuttingBean.setLinkmoreUrl("http://192.168.1.211:8081");
		abuttingBean.setLinkmoreNewUrl("http://192.168.1.211:8081");
		lockFactory.setAbuttingBean(abuttingBean);
		ResponseMessage<LockBean> res =	lockFactory.lockDown("ECCB1E57A0CD111");
		System.out.println(res.getMsgCode());
		
	}
	
	
}


