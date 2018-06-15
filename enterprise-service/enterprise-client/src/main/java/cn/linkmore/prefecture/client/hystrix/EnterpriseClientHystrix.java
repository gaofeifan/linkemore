package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.EnterpriseClient;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class EnterpriseClientHystrix implements EnterpriseClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service target_month list() hystrix");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("prefecture service target_month tree() hystrix");
		return null;
	}
	
}
