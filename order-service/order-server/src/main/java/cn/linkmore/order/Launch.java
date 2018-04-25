package cn.linkmore.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" })
public class Launch {
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
}
