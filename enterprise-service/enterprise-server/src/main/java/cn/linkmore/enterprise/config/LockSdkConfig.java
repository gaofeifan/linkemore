/**
 * 
 */
package cn.linkmore.enterprise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.linkmore.lock.bean.AbuttingBean;
import com.linkmore.lock.factory.LockFactory;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@Configuration
@Component
public class LockSdkConfig {
	
	@Value("${sdk.linkmore_url}")
	private String linkmoreUrl;
	
	@Bean
	public LockFactory getLockFactory(){
		LockFactory lockFactory = LockFactory.getInstance();
		//需要配置锁平台url
		AbuttingBean abuttingBean = new AbuttingBean();
		abuttingBean.setLinkmoreUrl(linkmoreUrl);
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}
}
