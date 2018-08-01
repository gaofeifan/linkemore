package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping(value = "/v2.0/upload/file/{id}", method = RequestMethod.POST)  
	public void uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id")Long id){
		this.ossService.uploadFile(file,id);
	}
	
	@RequestMapping(value = "/v2.0/upload/image/{id}", method = RequestMethod.POST)  
	public void uploadImage(@RequestParam("image") MultipartFile image,@PathVariable("id")Long id){
		this.ossService.uploadImage(image,id);
	} 
}
