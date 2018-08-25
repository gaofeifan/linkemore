package cn.linkmore.prefecture.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lock")
public class LockProperties {
	private String linkmoreUrl;
	private String linkmoreNewUrl; 
	
	public String getLinkmoreUrl() {
		return linkmoreUrl;
	}
	public void setLinkmoreUrl(String linkmoreUrl) {
		this.linkmoreUrl = linkmoreUrl;
	} 
	public String getLinkmoreNewUrl() {
		return linkmoreNewUrl;
	}
	public void setLinkmoreNewUrl(String linkmoreNewUrl) {
		this.linkmoreNewUrl = linkmoreNewUrl;
	}
	
}
