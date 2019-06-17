package cn.linkmore.account.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "guomaofuwu.pay")
public class GuoMaoPayConfig {
	private  String appKey;
	private  String paySecretKey;
	private  String callBackSecretKey;
	private  Long payerUserId;
	private  Long payeeUserId;
	private  String namespaceId;
	private  String baseUri;
	private  String payHomePath;
	private  String refundOrderPath;
	private  String queryOrderPath;
	private  String callBackUri;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getPaySecretKey() {
		return paySecretKey;
	}
	public void setPaySecretKey(String paySecretKey) {
		this.paySecretKey = paySecretKey;
	}
	public String getCallBackSecretKey() {
		return callBackSecretKey;
	}
	public void setCallBackSecretKey(String callBackSecretKey) {
		this.callBackSecretKey = callBackSecretKey;
	}
	public Long getPayerUserId() {
		return payerUserId;
	}
	public void setPayerUserId(Long payerUserId) {
		this.payerUserId = payerUserId;
	}
	public Long getPayeeUserId() {
		return payeeUserId;
	}
	public void setPayeeUserId(Long payeeUserId) {
		this.payeeUserId = payeeUserId;
	}
	public String getNamespaceId() {
		return namespaceId;
	}
	public void setNamespaceId(String namespaceId) {
		this.namespaceId = namespaceId;
	}
	public String getBaseUri() {
		return baseUri;
	}
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	public String getPayHomePath() {
		return payHomePath;
	}
	public void setPayHomePath(String payHomePath) {
		this.payHomePath = payHomePath;
	}
	public String getRefundOrderPath() {
		return refundOrderPath;
	}
	public void setRefundOrderPath(String refundOrderPath) {
		this.refundOrderPath = refundOrderPath;
	}
	public String getQueryOrderPath() {
		return queryOrderPath;
	}
	public void setQueryOrderPath(String queryOrderPath) {
		this.queryOrderPath = queryOrderPath;
	}
	public String getCallBackUri() {
		return callBackUri;
	}
	public void setCallBackUri(String callBackUri) {
		this.callBackUri = callBackUri;
	}

}
