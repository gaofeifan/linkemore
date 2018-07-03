package cn.linkmore.prefecture.client.hystrix;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqStrategy;
/**
 * 远程调用实现 - 计费策略信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StrategyBaseClientHystrix implements StrategyBaseClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Map<String, Object> fee(ReqStrategy reqStrategy) {
		log.info("prefecture service strategy fee(Long strategyId) hystrix");
		return new HashMap<String,Object>();
	}

}
