package cn.linkmore.coupon.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.StrategyClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqStrategy;

@Component
public class StrategyClientHystrix implements StrategyClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int save(ReqStrategy record) {
		log.info("coupon service strategy save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqStrategy record) {
		log.info("coupon service strategy update() hystrix");
		return 0;
	}

	@Override
	public int delete(Long id) {
		log.info("coupon service strategy delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service strategy check() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service strategy list() hystrix");
		return null;
	}
	
}
