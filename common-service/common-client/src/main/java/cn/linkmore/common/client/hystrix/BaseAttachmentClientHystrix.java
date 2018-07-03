package cn.linkmore.common.client.hystrix;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.response.ResAttachment;
import cn.linkmore.common.response.ResBaseAttachment;

/**
 * 文件管理
 * @author   jiaohanbin
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@Component
public class BaseAttachmentClientHystrix implements BaseAttachmentClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean delete(Long id) {
		log.info("common service boolean delete(Long id) hystrix");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("common service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public ResBaseAttachment imageUpload(MultipartFile file) {
		log.info("common service ResBaseAttachment imageUpload(MultipartFile file) hystrix");
		return null;
	}

	@Override
	public ResBaseAttachment fileUpload(MultipartFile file) {
		log.info("common service ResBaseAttachment fileUpload(MultipartFile file) hystrix");
		log.info("common service ResBaseAttachment fileUpload(MultipartFile file) hystrix");
		return null;
	}

	@Override
	public String createImage(String fileName, InputStream is) {
		log.info("common service String createImage(String fileName, InputStream is) hystrix");
		return null;
	}

	@Override
	public ResBaseAttachment find(Long id) {
		log.info("common service ResBaseAttachment find(Long id) hystrix");
		return null;
	}

	@Override
	public void save(ReqAttachment image) {
		log.info("common service ResBaseAttachment save(ReqAttachment image) hystrix");
	}

	
	
	
	
}
