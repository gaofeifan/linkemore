package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	/**
	 * 普通文件上传
	 * @param file 文件名
	 * @param id 对应Attachment主键
	 */
	@RequestMapping(value = "/v2.0/upload/file", method = RequestMethod.PUT) 
	@ResponseBody
	public void uploadFile(@RequestPart(value = "file", required = true) MultipartFile file,@RequestParam(value = "id", required = true)Long id);
	
	/**
	 * 图片文件上传
	 * @param image 图片名
	 * @param id 对应Attachment主键
	 */
	@RequestMapping(value = "/v2.0/upload/image", method = RequestMethod.PUT) 
	@ResponseBody
	public void uploadImage(@RequestPart(value = "image", required = true) MultipartFile image,@RequestParam(value = "id", required = true)Long id);

	
}
