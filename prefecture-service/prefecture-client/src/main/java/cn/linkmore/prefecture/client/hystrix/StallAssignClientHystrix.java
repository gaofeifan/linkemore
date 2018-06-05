package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallAssignClient;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallAssignClientHystrix implements StallAssignClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall_assign list() hystrix");
		return null;
	}
}
