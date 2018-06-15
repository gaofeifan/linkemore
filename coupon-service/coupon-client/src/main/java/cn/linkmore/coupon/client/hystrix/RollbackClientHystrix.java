package cn.linkmore.coupon.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.RollbackClient;

@Component
public class RollbackClientHystrix implements RollbackClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service roll_back list() hystrix");
		return null;
	}
	
}
