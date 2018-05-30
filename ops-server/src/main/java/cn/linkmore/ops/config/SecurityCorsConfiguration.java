package cn.linkmore.ops.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityCorsConfiguration {
	
	/*@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost");
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}*/
	
	 	@Bean
	    public FilterRegistrationBean filterRegistrationBean() {
	        // 对响应头进行CORS授权
	        MyCorsRegistration corsRegistration = new MyCorsRegistration("*");
	        List<String> allowedOrigins = new ArrayList<>();
	        allowedOrigins.add("http://localhost");
	        String[] objects = allowedOrigins.toArray(new String[allowedOrigins.size()]);

	        corsRegistration
	                //允许向该服务器提交请求的URI，*表示全部允许
	                .allowedOrigins(objects)
	                //允许提交请求的方法，*表示全部允许
	                .allowedMethods(HttpMethod.DELETE.name(), HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name())
	                //允许的头信息,*标识全部允许
	                .allowedHeaders(CrossOrigin.DEFAULT_ALLOWED_HEADERS)
	                //暴露的头信息
	                .exposedHeaders(HttpHeaders.SET_COOKIE)
	                //允许Cookie跨域，在做登录校验的时候有用
	                .allowCredentials(CrossOrigin.DEFAULT_ALLOW_CREDENTIALS)
	                //预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
	                .maxAge(CrossOrigin.DEFAULT_MAX_AGE);

	        // 注册CORS过滤器
	        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
	        //第一个参数表示过滤的url,*表示过滤所有
	        configurationSource.registerCorsConfiguration("/**", corsRegistration.getCorsConfiguration());
	        CorsFilter corsFilter = new CorsFilter(configurationSource);

	        return new FilterRegistrationBean(corsFilter);
	    }

}

