package cn.linkmore.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import zipkin2.Span;
import zipkin2.reporter.Reporter;

@SpringBootApplication 
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" })
@EnableHystrix
public class Launch {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	} 
	@Bean
	@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
	public Reporter<Span> spanReporter() {
		return new Reporter<Span>() {
			@Override
			public void report(Span span) {
				log.info(span.toString());
			}
		};
	}
}
