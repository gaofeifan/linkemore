package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config - 极光推送
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "push")
@Component
public class PushConfig {
	
	private String key;
	private String secret;
	
	private String keyAdd;
	private String secretAdd;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getKeyAdd() {
		return keyAdd;
	}
	public void setKeyAdd(String keyAdd) {
		this.keyAdd = keyAdd;
	}
	public String getSecretAdd() {
		return secretAdd;
	}
	public void setSecretAdd(String secretAdd) {
		this.secretAdd = secretAdd;
	}

	


	
	
	
}
