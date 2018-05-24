package cn.linkmore.security.client.hystrix;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.PageElementClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPageElement;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PageElementClientHystrix implements PageElementClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqPageElement page) {
		log.info("pageElement save");
	}

	@Override
	public void update(ReqPageElement page) {
		log.info("pageElement update");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("pageElement delete");
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("pageElement check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("pageElement list");
		return null;
	}

	@Override
	public Map<String, Object> map() {
		log.info("pageElement map");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("pageElement tree");
		return null;
	}

}
