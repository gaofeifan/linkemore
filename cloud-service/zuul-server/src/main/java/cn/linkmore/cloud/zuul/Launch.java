package cn.linkmore.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul server
 * @author liwenlong
 * @version 1.0
 *
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class Launch {

	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

}
