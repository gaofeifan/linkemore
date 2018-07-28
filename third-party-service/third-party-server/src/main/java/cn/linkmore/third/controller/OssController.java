package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value = "/v2.0/upload/file", method = RequestMethod.PUT) 
	@ResponseBody
	public void uploadFile(@RequestPart(value = "file", required = true) MultipartFile file,@RequestParam(value = "id", required = true)Long id){
		this.ossService.uploadFile(file,id);
	}
	
	@RequestMapping(value = "/v2.0/upload/image", method = RequestMethod.PUT) 
	@ResponseBody
	public void uploadImage(@RequestPart(value = "image", required = true) MultipartFile image,@RequestParam(value = "id", required = true)Long id){
		this.ossService.uploadImage(image,id);
	} 
}
