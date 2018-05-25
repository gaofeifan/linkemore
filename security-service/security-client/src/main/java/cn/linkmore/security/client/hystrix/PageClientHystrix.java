package cn.linkmore.security.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.PageClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPage;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PageClientHystrix implements PageClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPage page) {
		log.info("page save");
		return 0;
	}

	@Override
	public int update(ReqPage page) {
		log.info("page update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("page delete");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("page check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("page list");
		return null;
	}

}
