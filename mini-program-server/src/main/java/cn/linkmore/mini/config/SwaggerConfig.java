package cn.linkmore.mini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("cn.linkmore.mini.controller")).paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() { 
		return new ApiInfoBuilder()
				.title("微信小程序接口")
				.description("本文档仅支持微信小程序前端调用完全基于RESTful接口说明") 
				.termsOfServiceUrl("http://www.linkmoreparking.com")
				.version("2.0")  
				.contact(new Contact("凌猫停车",null,null))
				.build();
	}
}
