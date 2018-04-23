package cn.linkmore.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka server
 * @author liwenlong
 * @version 1。0
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }
}