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
	public int save(ReqClazz clazz) {
		log.info("clazz save hystrix");
		return 0;
	}

	@Override
	public int update(ReqClazz reqClazz) {
		log.info("clazz update hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("clazz delete hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("clazz check hystrix");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("clazz list hystrix");
		return new ViewPage(0,0,null);
	}

}
