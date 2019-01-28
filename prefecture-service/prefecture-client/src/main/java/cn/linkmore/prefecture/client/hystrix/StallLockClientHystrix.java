package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallLockClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;
/**
 * 远程调用实现 - 车位锁
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class StallLockClientHystrix implements StallLockClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall_lock list() hystrix");
		return null;
	}

	@Override
	public int save(ReqStallLock record) {
		log.info("prefecture service stall_lock save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqStallLock record) {
		log.info("prefecture service stall_lock update() hystrix");
		return 0;
	}

	@Override
	public int delete(Long id) {
		log.info("prefecture service stall_lock delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service stall_lock check() hystrix");
		return null;
	}

	@Override
	public int batchSave(List<ReqStallLock> locks) {
		log.info("prefecture service stall_lock batchSave() hystrix");
		return 0;
	}

	@Override
	public int checkSn(String sn) {
		log.info("prefecture service stall_lock checkSn() hystrix");
		return 0;
	}

	@Override
	public boolean checkFormerSn(Map<String, Object> param) {
		log.info("prefecture service stall_lock checkFormerSn() hystrix");
		return false;
	}

	@Override
	public List<ResStallLock> findList(Map<String, Object> param) {
		log.info("prefecture service stall_lock findList() hystrix");
		return null;
	}

	@Override
	public List<ResStallLock> findAll(Map<String, Object> param) {
		log.info("prefecture service stall_lock findAll() hystrix");
		return null;
	}
}
