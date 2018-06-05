package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.LockOperateLogClient;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;
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
	public int save(ReqLockOperateLog reqLock) {
		log.info("prefecture service locklog save(reqLock) hystrix");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service locklog list(pageable) hystrix");
		return null;
	}

	@Override
	public List<ResLockOperateLog> detail(Long id) {
		log.info("prefecture service locklog detail(id) hystrix");
		return null;
	}

	@Override
	public List<ResLockOperateLog> export(ReqLockOperateLogExcel bean) {
		log.info("prefecture service locklog export(bean) hystrix");
		return null;
	}
}
