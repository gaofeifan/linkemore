package cn.linkmore.cloud.hystrix;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Hystrix dashboard
 * @author liwenlong
 * @version 1.0
 */
@SpringBootApplication
@EnableHystrixDashboard
public class Launch {

	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
}