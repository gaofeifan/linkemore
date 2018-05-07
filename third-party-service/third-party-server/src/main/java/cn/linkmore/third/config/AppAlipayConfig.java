package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config - 支付宝
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "app-alipay")
@Component
public class AppAlipayConfig {
	private String sellerId;
	private String appId;
	private String serviceUrl;
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
}
