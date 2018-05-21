package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.LockOperateLogClient;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class LockOperateLogClientHystrix implements LockOperateLogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqLockOperateLog reqLock) {
		log.info("prefecture service locklog save(ReqLockOperateLog reqLock) hystrix");
	}
}
