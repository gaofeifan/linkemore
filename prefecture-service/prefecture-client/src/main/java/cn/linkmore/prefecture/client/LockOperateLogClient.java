package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.LockOperateLogClientHystrix;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;
/**
 * 远程调用 - 锁操作日志详情
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/lock", fallback=LockOperateLogClientHystrix.class,configuration = FeignConfiguration.class)
public interface LockOperateLogClient {
	
	/**
	 * 保存锁操作日志
	 * 
	 * @param reqLock
	 */
	@RequestMapping(value = "/v2.0/save", method=RequestMethod.POST)
	public int save(@RequestBody ReqLockOperateLog reqLock) ;
	
	/*
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResLockOperateLog> detail(@RequestBody Long id);
	/*
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResLockOperateLog> export(@RequestBody ReqLockOperateLogExcel bean);
	
}
