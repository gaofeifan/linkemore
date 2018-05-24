package cn.linkmore.security.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.LogClient;
/**
 * 远程调用实现 - 日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class LogClientHystrix implements LogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("log list");
		return null;
	}

}
