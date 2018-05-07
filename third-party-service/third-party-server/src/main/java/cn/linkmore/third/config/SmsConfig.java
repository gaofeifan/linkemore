package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Config - 阿里云短信
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "sms")
@Component
public class SmsConfig { 
	private String regionId;
	private String accessKey;
	private String accessSecret;
	private String endpointName;
	private String signName;
	 
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getAccessSecret() {
		return accessSecret;
	}
	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}
	public String getEndpointName() {
		return endpointName;
	}
	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	} 
}
