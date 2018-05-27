package cn.linkmore.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lock")
public class LockProperties {
	private String huaqingUrl; 
	private String linkmoreUrl;
	public String getHuaqingUrl() {
		return huaqingUrl;
	}
	public void setHuaqingUrl(String huaqingUrl) {
		this.huaqingUrl = huaqingUrl;
	}
	public String getLinkmoreUrl() {
		return linkmoreUrl;
	}
	public void setLinkmoreUrl(String linkmoreUrl) {
		this.linkmoreUrl = linkmoreUrl;
	} 
}
