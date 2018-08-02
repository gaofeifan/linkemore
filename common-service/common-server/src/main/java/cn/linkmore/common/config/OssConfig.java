package cn.linkmore.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Config - OSS配置
 * @author liwenlong
 * @version 2.0
 */
@ConfigurationProperties(prefix = "oss")
@Component
public class OssConfig {
	private String accessKeyId;
	private String accessKeySecret;
	private String uploadEndpoint;
	private String downloadEndpoint;
	private String bucketName;
	private long downloadUrlExpiration;
	private String tempDir;
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getUploadEndpoint() {
		return uploadEndpoint;
	}
	public void setUploadEndpoint(String uploadEndpoint) {
		this.uploadEndpoint = uploadEndpoint;
	}
	public String getDownloadEndpoint() {
		return downloadEndpoint;
	}
	public void setDownloadEndpoint(String downloadEndpoint) {
		this.downloadEndpoint = downloadEndpoint;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public long getDownloadUrlExpiration() {
		return downloadUrlExpiration;
	}
	public void setDownloadUrlExpiration(long downloadUrlExpiration) {
		this.downloadUrlExpiration = downloadUrlExpiration;
	}
	public String getTempDir() {
		return tempDir;
	}
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	

}
