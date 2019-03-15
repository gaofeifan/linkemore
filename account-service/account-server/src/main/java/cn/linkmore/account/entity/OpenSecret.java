package cn.linkmore.account.entity;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "opensecret")
public class OpenSecret {

	private Map<String,Object> secrets;


	public Map<String, Object> getSecrets() {
		return secrets;
	}

	/**
	 * @param secrets the secrets to set
	 */
	public void setSecrets(Map<String, Object> secrets) {
		this.secrets = secrets;
	}
	
	

	
}
