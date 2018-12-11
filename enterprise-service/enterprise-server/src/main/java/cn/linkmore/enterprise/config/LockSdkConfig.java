/**
 * 
 */
package cn.linkmore.enterprise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@Configuration
@Component
public class LockSdkConfig {
	
	@Value("${sdk.linkmore-url}")
	private String linkmoreUrl;
	@Value("${sdk.linkmore-new-url}")
	private String linkmoreNewUrl;
	
	/*@Bean
	public LockFactory getLockFactory(){
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		abuttingBean.setLinkmoreUrl(linkmoreUrl);
		abuttingBean.setLinkmoreNewUrl(linkmoreNewUrl);
		lockFactory.setAbuttingBean(abuttingBean);
		return lockFactory;
	}*/
	/*
	public static void main(String[] args) {
		LockFactory lockFactory = LockFactory.getInstance();
		AbuttingBean abuttingBean = new AbuttingBean();
		abuttingBean.setLinkmoreUrl("http://192.168.1.211:8081");
		abuttingBean.setLinkmoreNewUrl("http://192.168.1.211:8081");
		lockFactory.setAbuttingBean(abuttingBean);
		ResponseMessage<LockBean> rs = lockFactory.lockDown("FCAEE5D0E27E");
		System.out.println(  rs.getMsg());
	}*/
	
}
