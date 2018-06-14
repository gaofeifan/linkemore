package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliyun.oss.OSSClient;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.OssClientHystrix; 

/**
 * Client - Oss文件服务
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "third-party-server", path = "/feign/oss", fallback=OssClientHystrix.class,configuration = FeignConfiguration.class)
public interface OssClient {
	@RequestMapping(value = "/v2.0/upload", method = RequestMethod.GET) 
	@ResponseBody
	public OSSClient uploadOSSClient();

	@RequestMapping(value = "/v2.0/download", method = RequestMethod.GET) 
	@ResponseBody
	public OSSClient downloadOSSClient();
	
}
