package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResStallEntity;
/**
 * 远程调用实现 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallClientHystrix implements StallClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean order(String lockSn) {
		log.info("prefecture service stall order(String lockSn) hystrix");
		return false;
	}

	@Override
	public boolean cancel(Long stallId) {
		log.info("prefecture service stall cancel(Long stallId) hystrix");
		return false;
	}

	@Override
	public Boolean downlock(Long stallId) {
		log.info("prefecture service stall downlock(Long stallId) hystrix");
		return false;
	}

	@Override
	public Boolean uplock(Long stallId) {
		log.info("prefecture service stall uplock(Long stallId) hystrix");
		return false;
	}

	@Override
	public Boolean checkout(Long stallId) {
		log.info("prefecture service stall checkout(Long stallId) hystrix");
		return false;
	}

	@Override
	public ResStallEntity findById(Long stallId) {
		log.info("prefecture service stall findById(Long stallId) hystrix");
		return new ResStallEntity();
	}
}
