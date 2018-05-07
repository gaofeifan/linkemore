package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "base")
@Component
public class BaseConfig {
	private String serviceUrl;
	private Boolean online;
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public Boolean getOnline() {
		return online;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
}
