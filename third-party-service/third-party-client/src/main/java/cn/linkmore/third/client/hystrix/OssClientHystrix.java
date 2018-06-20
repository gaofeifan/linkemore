package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;

import cn.linkmore.third.client.OssClient;
import cn.linkmore.third.response.ResOssConfig;

@Component
public class OssClientHystrix implements OssClient {

	@Override
	public OSSClient uploadOSSClient() {
		return null;
	}

	@Override
	public OSSClient downloadOSSClient() {
		return null;
	}

	@Override
	public ResOssConfig initOssConfig() {
		return null;	
	}
}
