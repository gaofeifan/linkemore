package cn.linkmore.user;

import java.util.Date;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.controller.AccessDetailAop;

@SpringBootApplication
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" })
@EnableAsync
@EnableRedisHttpSession
@ControllerAdvice
public class Launch {
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> handleValidationException(ConstraintViolationException e) {
		for (ConstraintViolation<?> s : e.getConstraintViolations()) {
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION.code,s.getMessage(), AccessDetailAop.getRequest());
		}
		return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, AccessDetailAop.getRequest());
	}
}
