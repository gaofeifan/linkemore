package cn.linkmore.cloud.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.linkmore.cloud.zuul.filter.ErrorFilter;
import cn.linkmore.cloud.zuul.filter.OpenApiFilter;
import cn.linkmore.cloud.zuul.filter.RateLimiterFilter;
import cn.linkmore.cloud.zuul.filter.TokenFilter;
import cn.linkmore.cloud.zuul.handler.CustomErrorHandler;
 

@Configuration
public class FilterConfiguration { 
	
	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter(); 
	}
	@Bean
	public OpenApiFilter openApiFilter() {
		return new OpenApiFilter(); 
	}
	@Bean
	public RateLimiterFilter rateLimiterFilter() {
		return new RateLimiterFilter(); 
	}
	@Bean
	public ErrorFilter errorFilter(CustomErrorHandler errorHandler) {
		ErrorFilter filter = new ErrorFilter();
		filter.setErrorHandler(errorHandler);
		return filter;
	} 
	
}
