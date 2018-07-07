package cn.linkmore.monitor.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.monitor.client.FeignExceptionLogClient;
import cn.linkmore.monitor.request.ReqExceptionLog;
/**
 * @author   GFF
 * @Date     2018年7月4日
 * @Version  v2.0
 */
@Component
public class FeignExceptionLogClientHystrix implements FeignExceptionLogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void info(ReqExceptionLog rel) {
		log.info("monitor service exception-log info(ReqExceptionLog rel) hystrix");
	}

	@Override
	public void bindException(ReqExceptionLog rel) {
		log.info("monitor service exception-log bindException(ReqExceptionLog rel) hystrix");
	}

	@Override
	public void validException(ReqExceptionLog rel) {
		log.info("monitor service exception-log validException(ReqExceptionLog rel) hystrix");
	}

	@Override
	public void feignException(ReqExceptionLog rel) {
		log.info("monitor service exception-log feignException(ReqExceptionLog rel) hystrix");
	}

}
