package cn.linkmore.third.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.jpush.api.JPushClient;

@Configuration
@EnableConfigurationProperties
public class BeanFactory { 

	@Autowired
	private SmsConfig smsConfig; 

	@Autowired
	private PushConfig PushConfig;

	@Autowired
	private OssConfig ossConfig;

	@Bean(name = "iAcsClient")
	public IAcsClient iAcsClient() throws ClientException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		IClientProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKey(),
				smsConfig.getAccessSecret());
		DefaultProfile.addEndpoint(smsConfig.getEndpointName(), smsConfig.getRegionId(), "Dysmsapi",
				"dysmsapi.aliyuncs.com");
		return new DefaultAcsClient(profile);
	}

	@Bean(name = "jPushClient")
	public JPushClient jPushClient() {
		return new JPushClient(PushConfig.getSecret(), PushConfig.getKey(), 3);
	}
	
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
