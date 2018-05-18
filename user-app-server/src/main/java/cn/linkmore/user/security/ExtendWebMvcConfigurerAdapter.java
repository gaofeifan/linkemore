package cn.linkmore.user.security;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 自定义配置
 * @author liwenlong
 * @version 2.0
 *
 */
@Configuration
public class ExtendWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Resource
	private ApplicationContext applicationContext; 

	@Override
	public void addInterceptors(InterceptorRegistry registry) { 
		AccessInterceptor accessInterceptor = applicationContext.getBean(AccessInterceptor.class);  
		registry.addInterceptor(accessInterceptor);
	}
}
