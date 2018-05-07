package cn.linkmore.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
/**
 * Boot admin server
 * @author liwenlong
 * @version 1。0
 */
@Configuration 
public class Launch { 
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

} 