package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.third.client.OssClient;

@Component
public class OssClientHystrix implements OssClient {

	 
	public void uploadFile(@RequestPart(value = "file", required = true) MultipartFile file,@RequestParam(value = "id", required = true)Long id){
		throw new BusinessException(StatusEnum.THIRD_FILE_UPLOAD_ERROR);
	}
	
	 
	public void uploadImage(@RequestPart(value = "image", required = true) MultipartFile image,@RequestParam(value = "id", required = true)Long id){
		throw new BusinessException(StatusEnum.THIRD_IMAGE_UPLOAD_ERROR);
	} 
}
