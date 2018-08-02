package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.third.client.hystrix.OssClientHystrix;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * Client - Oss文件服务
 * 
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/feign/oss", fallback = OssClientHystrix.class/*, configuration = OssConfiguration.class*/)
public interface OssClient {
	/**
	 * 普通文件上传
	 * 
	 * @param file
	 *            文件名
	 * @param id
	 *            对应Attachment主键
	 */
	@RequestMapping(value = "/v2.0/upload/file/{id}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadFile(@RequestPart("file") MultipartFile file, @PathVariable("id") Long id);

	/**
	 * 图片文件上传
	 * 
	 * @param image
	 *            图片名
	 * @param id
	 *            对应Attachment主键
	 */
	@RequestMapping(value = "/v2.0/upload/image/{id}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadImage(@RequestPart("image") MultipartFile image, @PathVariable("id") Long id);

}

/*@Component
class OssConfiguration {

	@Bean
	@Primary
	@Scope("prototype")
	public Encoder multipartFormEncoder() {
		return new SpringFormEncoder();
	}
}*/
