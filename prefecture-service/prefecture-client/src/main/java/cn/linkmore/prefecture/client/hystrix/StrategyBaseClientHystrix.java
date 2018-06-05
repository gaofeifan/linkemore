package cn.linkmore.prefecture.client.hystrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.request.ReqStrategyBase;
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

	@Override
	public int save(ReqStrategyBase reqStrategyBase) {
		log.info("prefecture service strategy save(Long reqStrategyBase) hystrix");
		return 0;
	}

	@Override
	public int update(ReqStrategyBase reqStrategyBase) {
		log.info("prefecture service strategy update(Long reqStrategyBase) hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service strategy delete(Long ids) hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service strategy check(Long ids) hystrix");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service strategy list(ViewPageable pageable) hystrix");
		return null;
	}
}
