package cn.linkmore.security.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.security.client.hystrix.PageElementClientHystrix;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPageElement;
/**
 * 远程调用 - 页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "security-server", path = "/page_element", fallback=PageElementClientHystrix.class,configuration = FeignConfiguration.class)
public interface PageElementClient {
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPageElement reqPageElement);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPageElement reqPageElement);
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/map", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> map();
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree tree();
}
