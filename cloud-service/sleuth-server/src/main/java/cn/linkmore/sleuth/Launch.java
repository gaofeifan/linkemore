package cn.linkmore.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;
/**
 * Sleuth server
 * @author liwenlong
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class Launch {

	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}

}
