package cn.linkmore.ops.config;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.linkmore.ops.shiro.AuthenticationFilter;
import cn.linkmore.ops.shiro.AuthenticationRealm;
import cn.linkmore.ops.shiro.FilterChainService;

/**
 * 配置 - Shiro
 * @author liwl
 * @version 1.0
 *
 */
@Configuration  
@EnableConfigurationProperties
public class ShiroConfig {
	
	private  Logger logger = LoggerFactory.getLogger(this.getClass()); 
	 
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter")); 
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*"); 
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
	} 
	
	@Bean(name="filterChainService")
	public FilterChainService filterChainService(){
		return new FilterChainService();
	}
	
	
	@Bean(name="authenticationFilter")
	public AuthenticationFilter authenticationFilter(){
		return new AuthenticationFilter();
	}
	 /*
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,@Qualifier("authenticationFilter") AuthenticationFilter authenticationFilter,@Qualifier("filterChainService")FilterChainService filterChainService) throws Exception{
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/admin/auth/login");
		//bean.setSuccessUrl("https://api.linkmoreparking.cn/admin/auth/login");
		bean.setUnauthorizedUrl("/admin/auth/403");
		Map<String, Filter>filters = new HashMap<String,Filter>();
		//filters.put("authc", authenticationFilter);
		//filters.put("anon", new AnonymousFilter());
		bean.setFilters(filters);
		Map<String, String> chains = new HashMap<String,String>();
		chains.put("/admin/auth/login", "anon");
		
		chains.put("/admin/auth/**", "authc");
		chains.put("/admin/anon/**", "anon");
		chains.put("/admin/frame/**", "authc");
		
		Map<String,String> map = filterChainService.getFilterChainMap();
		Set<String> keys = map.keySet();
		for(String key:keys) {
			chains.put(key, map.get(key));
		}
		chains.put("/admin/**", "authc");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}
	*/
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,@Qualifier("authenticationFilter") AuthenticationFilter authenticationFilter,@Qualifier("filterChainService")FilterChainService filterChainService) throws Exception{
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/admin/auth/login");
		bean.setUnauthorizedUrl("/admin/auth/403");
		/*
		Map<String, Filter>filters = new HashMap<String,Filter>();
		filters.put("authc", authenticationFilter);
		filters.put("anon", new AnonymousFilter());
		bean.setFilters(filters);
		*/
		Map<String, String> chains = new LinkedHashMap<String,String>();
		chains.put("/admin/auth/login", "anon");
		chains.put("/admin/anon/**", "anon");
		chains.put("/admin/**", "authc");
		
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	
	}
	 
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("authenticationRealm") AuthenticationRealm authenticationRealm,@Qualifier("cacheManager") EhCacheManager cacheManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authenticationRealm );
		manager.setCacheManager(cacheManager);
		manager.setSessionManager(defaultWebSessionManager());
		return manager;
	}
	
	 
	@Bean(name="sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setGlobalSessionTimeout(1800000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);
		return sessionManager;
	}
	
	 
	@Bean(name="authenticationRealm")
	public AuthenticationRealm authenticationRealm() {
		AuthenticationRealm userRealm = new AuthenticationRealm();
		userRealm.setCacheManager(cacheManager());
		return userRealm;
	}
	
	 
	
	@Bean(name="cacheManager")
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return cacheManager;
	}
	
	@Bean(name="lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
}
