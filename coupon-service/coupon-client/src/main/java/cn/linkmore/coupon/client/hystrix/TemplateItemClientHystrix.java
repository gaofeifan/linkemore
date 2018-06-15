package cn.linkmore.coupon.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.coupon.client.TemplateItemClient;
import cn.linkmore.coupon.request.ReqTemplateItem;

@Component
public class TemplateItemClientHystrix implements TemplateItemClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqTemplateItem record) {
		log.info("coupon service temp_item save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqTemplateItem record) {
		log.info("coupon service temp_item update() hystrix");
		return 0;
	}
	
}
