package cn.linkmore.common.service;

import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.Attachment;

/**
 * 	文件管理
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
public interface AttachmentService {

	ViewPage findPage(ViewPageable pageable);

	Attachment saveImage(MultipartFile file);

	Attachment saveFile(MultipartFile file);


	boolean delete(Long id);

	Attachment find(Long id);


}
