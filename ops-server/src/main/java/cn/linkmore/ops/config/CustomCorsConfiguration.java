package cn.linkmore.ops.config;

import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomCorsConfiguration extends WebMvcConfigurerAdapter {

	@Resource
	private ApplicationContext applicationContext;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		AccessInterceptor accessInterceptor = applicationContext.getBean(AccessInterceptor.class);
		registry.addInterceptor(accessInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowCredentials(true);

	}

}