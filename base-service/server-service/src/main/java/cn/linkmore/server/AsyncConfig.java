package cn.linkmore.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "async")
public class AsyncConfig {
	private Integer corePoolSize;
	 
	private Integer maxPoolSize;
 
	private Integer keepAliveSeconds;
 
	private Integer queueCapacity;

	public Integer getCorePoolSize() {
		if(this.corePoolSize==null) {
			corePoolSize = 100;
		}
		return corePoolSize;
	}

	public void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public Integer getMaxPoolSize() {
		if(this.maxPoolSize==null) {
			maxPoolSize = 2000;
		}
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getKeepAliveSeconds() {
		if(this.keepAliveSeconds==null) {
			keepAliveSeconds = 60;
		}
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(Integer keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public Integer getQueueCapacity() {
		if(this.queueCapacity==null) {
			queueCapacity = 24000;
		}
		return queueCapacity;
	}

	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}
