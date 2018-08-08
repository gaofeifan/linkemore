package cn.linkmore.enterprise.interceptor;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 的辅助配置, 用来注册拦截器.
 * 
 */
@Configuration
public class DefaultWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Resource
	private ApplicationContext applicationContext;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 访问权限控制
		AccessControlInterceptor accessControlInterceptor = applicationContext.getBean(AccessControlInterceptor.class);
		registry.addInterceptor(accessControlInterceptor);
	}
}
