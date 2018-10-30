package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsPrefectureStrategyClient;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;
/**
 * 远程调用实现-断熔器 - 分组策略信息
 * @author lilinhai
 * @version 1.0
 *
 */ 
@Component
public class OpsPrefectureStrategyClientHystrix implements OpsPrefectureStrategyClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPrefectureStrategy reqPrefectureStrategy) {
		log.info("prefecture service strategy save(ReqPrefectureStrategy reqPrefectureStrategy) hystrix");
		return 0;
	}

	@Override
	public int update(ReqPrefectureStrategy reqPrefectureStrategy) {
		log.info("prefecture service strategy update(ReqPrefectureStrategy reqPrefectureStrategy) hystrix");
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
	public ResPrefectureStrategyNew selectByPrimaryKey(Long id) {
		log.info("prefecture service strategy ResPrefectureStrategyNew selectByPrimaryKey(Long id) hystrix");
		return new ResPrefectureStrategyNew();
	}

	@Override
	public List<ResStrategyFee> findList() {
		log.info("prefecture service strategy List<ResStrategyFee> findList() hystrix");
		return null;
	}

	@Override
	public int validateTime(Map<String, String> map) {
		log.info("prefecture service int validateTime Map<String, String> map hystrix");
		return 0;
	}

	@Override
	public int validateDate(Map<String, String> map) {
		log.info("prefecture service validateDate(Map<String, String> map) hystrix");
		return 0;
	}


}
