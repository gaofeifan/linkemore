package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.LockOperateLogClientHystrix;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
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
	public void save(@RequestBody ReqLockOperateLog reqLock) ;
	
}
