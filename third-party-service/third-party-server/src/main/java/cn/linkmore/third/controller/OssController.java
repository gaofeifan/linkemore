package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;

import cn.linkmore.third.config.OssConfig;
import cn.linkmore.third.response.ResOssConfig;
import cn.linkmore.third.service.OssService;
/**
 * Controller - OSS服务
 * @author liwenlong
 * @version 2.0
 */
@RestController
@RequestMapping("/feign/oss")
public class OssController {
	@Autowired
	private OssService ossService;
	
	@RequestMapping(value = "/v2.0/upload", method = RequestMethod.GET) 
	@ResponseBody
	public OSSClient uploadOSSClient(){
		return this.ossService.uploadOSSClient();
	}

	@RequestMapping(value = "/v2.0/download", method = RequestMethod.GET) 
	@ResponseBody
	public OSSClient downloadOSSClient(){
		return this.ossService.downloadOSSClient();
	}
	
	@RequestMapping(value = "/v2.0/init", method = RequestMethod.GET) 
	@ResponseBody
	public ResOssConfig initOssConfig(){
		OssConfig ossConfig = this.ossService.ossConfig();
		ResOssConfig config = null;
		if(ossConfig!=null) {
			config = new ResOssConfig();
			config.setAccessKeyId(ossConfig.getAccessKeyId());
			config.setAccessKeySecret(ossConfig.getAccessKeySecret());
			config.setBucketName(ossConfig.getBucketName());
			config.setDownloadEndpoint(ossConfig.getDownloadEndpoint());
			config.setDownloadUrlExpiration(ossConfig.getDownloadUrlExpiration());
			config.setTempDir(ossConfig.getTempDir());
			config.setUploadEndpoint(ossConfig.getUploadEndpoint()); 
		}
		return config;
	}
}
