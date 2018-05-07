package cn.linkmore.mini;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" }) 
@EnableHystrix
public class Launch {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	} 
}
