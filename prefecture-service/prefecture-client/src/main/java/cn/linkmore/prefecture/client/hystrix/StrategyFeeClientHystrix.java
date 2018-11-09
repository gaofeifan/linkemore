package cn.linkmore.prefecture.client.hystrix;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import cn.linkmore.prefecture.client.StrategyFeeClient;
/**
 * 远程调用实现 - 计费策略信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StrategyFeeClientHystrix implements StrategyFeeClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public Map<String, Object> amount(Map<String, Object> param) {
		log.info("amount param = {}",JSON.toJSON(param));
		return null;
	}

	

}
