package cn.linkmore.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSSClient;


@Configuration
@EnableConfigurationProperties
public class BeanFactory { 

	@Autowired
	private OssConfig ossConfig;

	/**
	 * 存储-下载
	 * 
	 * @return
	 */
	@Bean(name = "downloadOSSClient")
	public OSSClient downloadOSSClient() {
		return new OSSClient(ossConfig.getDownloadEndpoint(), ossConfig.getAccessKeyId(),
				ossConfig.getAccessKeySecret());
	}

	/**
	 * 存储-上传
	 * 
	 * @return
	 */
	@Bean(name = "uploadOSSClient")
	public OSSClient uploadOSSClient() {
		return new OSSClient(ossConfig.getUploadEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
	} 
}
