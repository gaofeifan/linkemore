package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
 */
@ConfigurationProperties(prefix = "loong-pay")
@Component
public class AppLoongPayConfig {
	
	private String merchantId;
	
	private String posId;
	
	private String branchId;
	
	private String url;

	private String pubKey;
	
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

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	@Override
	public String toString() {
		return "AppLoongPayConfig [merchantId=" + merchantId + ", posId=" + posId + ", branchId=" + branchId + ", url="
				+ url + ", pubKey=" + pubKey + "]";
	}
	
}
