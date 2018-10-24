package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Confifg - 银联
 * @author liwenlong
 * @version 1.0
 *
 */
@ConfigurationProperties(prefix = "union-pay")
@Component
public class UnionPayConfig { 
	private String unionServiceUrl;
	private String localServiceUrl;
	private String merId;
	private String certDir;
	private String certPath;
	private String certPwd;
	private String certType;
	private String encryptCertPath;
	private String middleCertPath;
	private String rootCertPath;
	private Boolean online; 
	public String getUnionServiceUrl() {
		return unionServiceUrl;
	}

	public void setUnionServiceUrl(String unionServiceUrl) {
		this.unionServiceUrl = unionServiceUrl;
	}

	public String getLocalServiceUrl() {
		return localServiceUrl;
	}

	public void setLocalServiceUrl(String localServiceUrl) {
		this.localServiceUrl = localServiceUrl;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getCertDir() {
		return certDir;
	}

	public void setCertDir(String certDir) {
		this.certDir = certDir;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getCertPwd() {
		return certPwd;
	}

	public void setCertPwd(String certPwd) {
		this.certPwd = certPwd;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getEncryptCertPath() {
		return encryptCertPath;
	}

	public void setEncryptCertPath(String encryptCertPath) {
		this.encryptCertPath = encryptCertPath;
	}

	public String getMiddleCertPath() {
		return middleCertPath;
	}

	public void setMiddleCertPath(String middleCertPath) {
		this.middleCertPath = middleCertPath;
	}

	public String getRootCertPath() {
		return rootCertPath;
	}

	public void setRootCertPath(String rootCertPath) {
		this.rootCertPath = rootCertPath;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	} 
}
