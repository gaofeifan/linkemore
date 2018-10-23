package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
@ConfigurationProperties(prefix = "app-wechat")
@Component
public class AppLongPayConfig {
	
	private String merchantId;
	
	private String posId;
	
	private String branchId;
	
	private String url;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
