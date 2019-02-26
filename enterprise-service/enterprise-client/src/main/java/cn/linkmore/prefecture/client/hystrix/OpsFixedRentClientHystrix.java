package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.prefecture.client.OpsFixedRentClient;

@Component
public class OpsFixedRentClientHystrix implements OpsFixedRentClient{
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("fxedRent service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}
	@Override
	public List<ResStall> stallList(Map<String, Object> map) {
		log.info("fxedRent service List<ResStall> stallList(Map<String, Object> map) hystrix");
		return null;
	}
	@Override
	public List<ResStall> freeStallList(Map<String, Object> map) {
		log.info("fxedRent service List<ResStall> freeStallList(Map<String, Object> map) hystrix");
		return null;
	}
	
	@Override
	public int insert(ReqFixedRent reqFixedRent) {
		log.info("fxedRent service int insert(ReqFixedRent reqFixedRent) hystrix");
		return 0;
	}
	
	@Override
	public int update(ReqFixedRent reqFixedRent) {
		log.info("fxedRent service int update(ReqFixedRent reqFixedRent) hystrix");
		return 0;
	}
	
	@Override
	public int deleteStall(Map<String, Object> map) {
		log.info("fxedRent service delete(Map<String, Object> map) hystrix");
		return 0;
	}
	@Override
	public int open(Map<String, Object> map) {
		log.info("fxedRent service intopen(Map<String, Object> map) hystrix");
		return 0;
	}
	@Override
	public int close(Map<String, Object> map) {
		log.info("fxedRent service close(Map<String, Object> map) hystrix");
		return 0;
	}
	@Override
	public ResFixedRent view(Long fixedId) {
		log.info("fxedRent service ResFixedRent view(Long fixedId) hystrix");
		return null;
	}
	@Override
	public String check(ReqFixedRent reqFixedRent) {
		log.info("fxedRent service String check(ReqFixedRent reqFixedRent) hystrix");
		return null;
	}
	
}
