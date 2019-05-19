package cn.linkmore.swagger;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;
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
	private String serviceUrl = "http://www.linkmoretech.cn";
	private String contact = "凌猫停车"; 
	private String basePackage = "";
	private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
	@Bean
	public Docket api() {
		log.info(" enable ======================= " + this.enable);
		if(this.enable) {
			return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
					.apis(SwaggerConfig.basePackage(this.basePackage)).paths(PathSelectors.any())
					.build();  
		}else {
			return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.none()).build();
			//return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(SwaggerConfig.basePackage("cn.linkmore.enterprise.controller.app,cn.linkmore.enterprise.controller.ent")).paths(PathSelectors.any()).build();
		} 
	}
	
	public static Predicate<RequestHandler> basePackage(final String basePackage) {
		log.info(" basePackage ======================= {} " + basePackage);
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
    }
    
    /**
     * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
     * 
     * @param basePackage 扫描包路径
     * @return Function
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return new Function<Class<?>, Boolean>() {
            
            @Override
            public Boolean apply(Class<?> input) {
                for (String strPackage : basePackage.split(",")) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
    
    /**
     * @param input RequestHandler
     * @return Optional
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
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
