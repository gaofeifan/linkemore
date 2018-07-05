package cn.linkmore.swagger;
 
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {
	private Boolean enable =  false;
	private String title = "应用接口";
	private String description = "本文档 完全基于RESTful接口说明";
	private String version = "2.0";
	private String serviceUrl = "http://www.linkmoreparking.com";
	private String contact = "凌猫停车"; 
	private String basePackage = "";
	@Bean
	public Docket api() {
		if(this.enable) {
			return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
					.apis(RequestHandlerSelectors.basePackage(this.basePackage)).paths(PathSelectors.any())
					.build();  
		}else {
			return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.none()).build();
		} 
	}

	private ApiInfo apiInfo() { 
		return new ApiInfoBuilder()
				.title(this.title)
				.description(this.description) 
				.termsOfServiceUrl(this.serviceUrl)
				.version(this.version)  
				.contact(new Contact(this.contact,null,null))
				.build();
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	} 
	
}
