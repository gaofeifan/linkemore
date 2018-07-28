package cn.linkmore.feign;
 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.Request;
import feign.Retryer;
@Configuration
public class FeignConfiguration {  
	
	@Bean  
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;  
    }
	
	@Bean
    Request.Options feignOptions() {
        return new Request.Options(5 * 1000, 30 * 1000);
    } 
	@Bean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }
}