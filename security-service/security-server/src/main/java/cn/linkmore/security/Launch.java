package cn.linkmore.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
 

 
@SpringBootApplication 
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" })
@EnableAsync
@EnableRedisHttpSession
public class Launch {
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
}
