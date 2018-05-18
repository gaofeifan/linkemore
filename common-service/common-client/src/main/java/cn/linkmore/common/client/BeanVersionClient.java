package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.common.client.hystrix.BeanVersionClientHystrix;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 	版本管理
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/version", fallback=BeanVersionClientHystrix.class,configuration = FeignConfiguration.class)
public interface BeanVersionClient {
	
	/**
	 * @Description  当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current/{source}",method = RequestMethod.GET)
	@ResponseBody
	public ResVersionBean current(@PathVariable("source")Short source);
	
	/**
	 * @Description  上报用户版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.GET)
	public void report(@RequestBody ReqVersion vrb);
}
