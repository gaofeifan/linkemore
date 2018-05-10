package cn.linkmore.ops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@EnableFeignClients 
@ComponentScan(basePackages = { "cn.linkmore" })
public class Launch {
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
}