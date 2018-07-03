package cn.linkmore.account.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.BlackListClientHystrix;
import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 权限模块--远程调用
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/feign/blacklist", fallback=BlackListClientHystrix.class,configuration = FeignConfiguration.class)
public interface BlackListClient {
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable);
	
	
	@RequestMapping(value = "/v2.0/status", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean status();
	
	@RequestMapping(value = "/v2.0/open", method = RequestMethod.PUT)
	@ResponseBody
	public ViewMsg open();
	@RequestMapping(value = "/v2.0/close", method = RequestMethod.DELETE)
	@ResponseBody
	public ViewMsg close();
	
	@RequestMapping(value = "/v2.0/enable", method = RequestMethod.PUT)
	@ResponseBody
	public ViewMsg enable(@RequestBody List<Long> list);

	@RequestMapping(value = "/v2.0/all", method = RequestMethod.GET)
	@ResponseBody
	public List<ResUserBlacklist> findList();
	
}
