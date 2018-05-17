package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;

@Component
public class PrefectureClientHystrix implements PrefectureClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ResPrefectureDetail findById(Long id) {
		log.info("prefecture service pres findById(Long id) hystrix");
		return new ResPrefectureDetail();
	}
	@Override
	public ResPrefectureStrategy findPreStrategy(Long preId) {
		log.info("prefecture service pres findPreStrategy(Long id) hystrix");
		return new ResPrefectureStrategy();
	}

}
