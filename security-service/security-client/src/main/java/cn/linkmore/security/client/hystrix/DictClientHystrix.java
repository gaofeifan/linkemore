package cn.linkmore.security.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.DictClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqDict;
import cn.linkmore.security.response.ResDict;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class DictClientHystrix implements DictClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqDict dict) {
		log.info("dict save");
		return 0;
	}

	@Override
	public int update(ReqDict reqClazz) {
		log.info("dict update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("dict delete");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("dict check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("dict list");
		return null;
	}

	@Override
	public List<ResDict> groupList(String code) {
		log.info("dict groupList");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("dict tree");
		return null;
	}

}
