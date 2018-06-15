package cn.linkmore.coupon.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.SendRecordClient;
import cn.linkmore.coupon.request.ReqSendRecord;

@Component
public class SendRecordClientHystrix implements SendRecordClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqSendRecord record) {
		log.info("coupon service send_record save() hystrix");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service send_record list() hystrix");
		return null;
	}

	@Override
	public int saveBusiness(ReqSendRecord record) {
		log.info("coupon service send_record saveBusiness() hystrix");
		return 0;
	}
	
}
