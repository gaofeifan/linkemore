package cn.linkmore.cloud.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@EnableDiscoveryClient
public class Launch { 
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

} 