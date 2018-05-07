package cn.linkmore.cloud.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Turbine server
 * @author liwenlong
 * @version 1。0  
 */
@SpringBootApplication
@EnableTurbine 
@EnableHystrix
@EnableHystrixDashboard
public class Launch {

	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

}
