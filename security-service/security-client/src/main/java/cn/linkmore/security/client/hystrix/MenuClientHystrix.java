package cn.linkmore.security.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.MenuClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqMenu;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class MenuClientHystrix implements MenuClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqMenu reqMenu) {
		log.info("menu save");
		return 0;
	}

	@Override
	public int update(ReqMenu reqMenu) {
		log.info("menu update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("menu delete");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("menu check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("menu list");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("menu tree");
		return null;
	}

	@Override
	public Map<String, Object> map() {
		log.info("menu map");
		return null;
	}

}
