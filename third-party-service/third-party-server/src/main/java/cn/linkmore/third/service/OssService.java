package cn.linkmore.third.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 阿里云OSS
 * @author liwenlong
 * @version 2.0
 *
 */
public interface OssService { 
	/**
	 * 上传文件
	 * @param file
	 * @param id
	 * @return
	 */
	public void uploadFile(MultipartFile file, Long id);

	/**
	 * 上传图片
	 * @param image
	 * @param id
	 * @return
	 */
	public void uploadImage(MultipartFile image, Long id);
}
