package cn.linkmore.cloud.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.config.EnableAdminServer;
/**
 * Boot admin server
 * @author liwenlong
 * @version 1。0
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer 
public class Launch { 
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

} 