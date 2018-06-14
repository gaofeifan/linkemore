package cn.linkmore.third.service;

import com.aliyun.oss.OSSClient;

import cn.linkmore.third.config.OssConfig;

/**
 * Service - 阿里云
 * @author liwenlong
 * @version 2.0
 *
 */
public interface OssService {
	public OSSClient uploadOSSClient();
	public OSSClient downloadOSSClient();
	public OssConfig ossConfig();
}
