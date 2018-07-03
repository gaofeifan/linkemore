package cn.linkmore.common.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.BaseAttachment;
import cn.linkmore.common.request.ReqAttachment;

/**
 * 	文件管理
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
public interface BaseAttachmentService {

	ViewPage findPage(ViewPageable pageable);

	BaseAttachment find(Long id);

	BaseAttachment saveImage(MultipartFile file);

	boolean delete(Long id);

	BaseAttachment saveFile(MultipartFile file);

	String createImage(String fileName, InputStream is);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqAttachment image);

}
