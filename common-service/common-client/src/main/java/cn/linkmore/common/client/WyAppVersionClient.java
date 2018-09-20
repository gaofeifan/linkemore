package cn.linkmore.common.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.BaseVersionClientHystrix;
import cn.linkmore.common.client.hystrix.WyAppVersionClientHystrix;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.request.ReqWyAppVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.response.ResWyAppVersion;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 	版本管理
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/feign/ent/version", fallback=WyAppVersionClientHystrix.class,configuration = FeignConfiguration.class)
public interface WyAppVersionClient {
	
	/**
	 * @Description  当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current/{source}",method = RequestMethod.GET)
	@ResponseBody
	public ResWyAppVersion current(@PathVariable("source")Integer source);
	
	/**
	 * @Description  上报用户版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public void report(@RequestBody ReqVersion vrb);

	@RequestMapping(value="/list-page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable);

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqWyAppVersion copyObject);

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqWyAppVersion copyObject);

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

	@RequestMapping(value="/check",method = RequestMethod.PUT)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check);

	
}
