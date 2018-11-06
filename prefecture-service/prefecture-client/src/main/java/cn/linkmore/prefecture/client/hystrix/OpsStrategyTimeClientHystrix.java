package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsStrategyTimeClient;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;
/**
 * 远程调用实现-断熔器 - 时段策略信息
 * @author lilinhai
 * @version 1.0
 *
 */ 
@Component
public class OpsStrategyTimeClientHystrix implements OpsStrategyTimeClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqStrategyTime reqStrategyBase) {
		log.info("prefecture service strategy save(Long reqStrategyBase) hystrix");
		return 0;
	}

	@Override
	public int update(ReqStrategyTime reqStrategyBase) {
		log.info("prefecture service strategy update(ReqStrategyTimeInterval reqStrategyBase) hystrix");
		return 0;
	}
	@Override
	public int updateStatus(Map<String, Object> map) {
		log.info("prefecture service strategy updateStatus(Map<String, Object> map) hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service strategy delete(Long ids) hystrix");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service strategy list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResStrategyTime> findList(Map<String, Object> map) {
		log.info("prefecture service strategy findList() hystrix");
		return new ArrayList<ResStrategyTime>();
	}
	@Override
	public ResStrategyTime selectByPrimaryKey(Long id) {
		log.info("prefecture service strategy ResStrategyTime hystrix");
		return new ResStrategyTime();
	}
}
