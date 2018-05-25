package cn.linkmore.security.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.DictGroupClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqDictGroup;
/**
 * 远程调用实现 - 字典分组信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class DictGroupClientHystrix implements DictGroupClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqDictGroup dict) {
		log.info("dict save");
		return 0;
	}

	@Override
	public int update(ReqDictGroup reqClazz) {
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

}
