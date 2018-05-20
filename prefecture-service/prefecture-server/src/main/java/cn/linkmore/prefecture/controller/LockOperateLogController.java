package cn.linkmore.prefecture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.service.LockOperateLogService;

/**
 * Controller - 锁操作日志
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/lock")
public class LockOperateLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LockOperateLogService logOperateLogService;

	/**
	 * 保存锁操作日志
	 * 
	 * @param reqLock
	 */
	@RequestMapping(value = "/v2.0/save", method=RequestMethod.POST)
	public void save(@RequestBody ReqLockOperateLog reqLock) {
		logOperateLogService.save(reqLock);
	}
}
