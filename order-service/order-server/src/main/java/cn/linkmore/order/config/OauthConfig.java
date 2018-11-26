package cn.linkmore.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix ="oauth")
@Component
public class OauthConfig {
	
	/*第一次重定向获取code  */
	private String codeUrl;
	/*第二次重定向到H5页面  */
	private String h5Url;
	
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}


	
}
