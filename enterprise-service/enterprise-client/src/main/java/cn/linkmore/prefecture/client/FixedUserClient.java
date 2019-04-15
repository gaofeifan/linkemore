package cn.linkmore.prefecture.client;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.FixedUserClientHystrix;

@FeignClient(value = "enterprise-server", path = "/fixed/user", fallback=FixedUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface FixedUserClient {


	//长租用户列表
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable);
	
	//开启
	@RequestMapping(value = "/pick",method = RequestMethod.POST)
	public void pick(@RequestBody ReqFixedUserPick reqFixedUserPick);
	
}
