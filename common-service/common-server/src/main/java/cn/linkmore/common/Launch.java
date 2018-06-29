package cn.linkmore.common;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;

@SpringBootApplication 
@EnableFeignClients(basePackages = { "cn.linkmore" })
@ComponentScan(basePackages = { "cn.linkmore" })
@EnableAsync 
@EnableHystrix
public class Launch {
	public static void main(String[] args) {
		SpringApplication.run(Launch.class, args);
	}
	@Bean
	public ServletRegistrationBean getServlet() {
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
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
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION.code,s.getMessage(), getRequest());
		}
		return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, getRequest());
	}
	

	/**
	 * @Description	获取httpservletrequest  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
