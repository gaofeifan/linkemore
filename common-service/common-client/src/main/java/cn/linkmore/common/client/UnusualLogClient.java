package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.common.client.hystrix.UnusualLogClientHystrix;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 数据词典
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/app_log", fallback=UnusualLogClientHystrix.class,configuration = FeignConfiguration.class)
public interface UnusualLogClient {
	
	/**
	 * @Description 新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void insert(@RequestBody ReqUnusualLog unusualLog);
}
