package cn.linkmore.security.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.ClazzClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqClazz;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class ClazzClientHystrix implements ClazzClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqClazz clazz) {
		log.info("clazz save");
	}

	@Override
	public void update(ReqClazz reqClazz) {
		log.info("clazz update");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("clazz delete");
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("clazz check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("clazz list");
		return null;
	}

}
