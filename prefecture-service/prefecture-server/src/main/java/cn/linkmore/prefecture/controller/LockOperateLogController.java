package cn.linkmore.prefecture.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;
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
	private LockOperateLogService lockOperateLogService;

	/**
	 * 保存锁操作日志
	 * 
	 * @param reqLock
	 */
	@RequestMapping(value = "/v2.0/save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqLockOperateLog reqLock) {
		return lockOperateLogService.save(reqLock);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.lockOperateLogService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResLockOperateLog> detail(@RequestBody Long id){
		return this.lockOperateLogService.findListById(id);
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResLockOperateLog> export(@RequestBody ReqLockOperateLogExcel bean){
		List<ResLockOperateLog> list = this.lockOperateLogService.exportList(bean);
		return list;
	}
}
