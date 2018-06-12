package cn.linkmore.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Zuul server
 * @author liwenlong
 * @version 1.0
 *
 */
@EnableZuulProxy 
@SpringBootApplication
@ComponentScan(basePackages = { "cn.linkmore" })
public class Launch {

	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

}
