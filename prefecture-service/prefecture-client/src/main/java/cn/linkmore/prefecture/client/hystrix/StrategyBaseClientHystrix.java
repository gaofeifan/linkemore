package cn.linkmore.prefecture.client.hystrix;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.StrategyBaseClient;

@Component
public class StrategyBaseClientHystrix implements StrategyBaseClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Map<String, Object> fee(Long strategyId, Date beginTime, Date endTime) {
		log.info("prefecture service strategy order(String lockSn) hystrix");
		return new HashMap<String,Object>();
	}
}