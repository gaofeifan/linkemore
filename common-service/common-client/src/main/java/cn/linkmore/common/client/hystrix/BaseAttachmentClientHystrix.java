package cn.linkmore.common.client.hystrix;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResBaseAttachment imageUpload(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResBaseAttachment fileUpload(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createImage(String fileName, InputStream is) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
