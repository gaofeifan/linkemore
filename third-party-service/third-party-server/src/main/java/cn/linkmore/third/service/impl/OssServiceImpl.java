package cn.linkmore.third.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aliyun.oss.OSSClient;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.config.OssConfig;
import cn.linkmore.third.service.OssService;

/**
 * Service - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class OssServiceImpl implements OssService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private OssConfig ossConfig;

	@Override
	public OSSClient uploadOSSClient() {
		return beanFactory.uploadOSSClient();
	}

	@Override
	public OSSClient downloadOSSClient() {
		return beanFactory.downloadOSSClient();
	}
	
	public OssConfig ossConfig() {
		return ossConfig;
	}
}
