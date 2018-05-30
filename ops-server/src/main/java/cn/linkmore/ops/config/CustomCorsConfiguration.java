package cn.linkmore.ops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomCorsConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://192.168.1.199", "*")
		.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
		.allowCredentials(true).maxAge(3600);
	}

}