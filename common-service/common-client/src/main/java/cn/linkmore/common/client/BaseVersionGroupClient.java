package cn.linkmore.common.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.BaseVersionGroupClientHystrix;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;
import cn.linkmore.feign.FeignConfiguration;

@FeignClient(value = "common-server", path = "/feign/version/user_group", fallback=BaseVersionGroupClientHystrix.class,configuration = FeignConfiguration.class)
public interface BaseVersionGroupClient {

	
	@RequestMapping(value="/insert",method = RequestMethod.POST)
	@ResponseBody
	public int insert(@RequestBody ReqBaseAppVersionGroup record);
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public int deleteByIds(@RequestBody List<Long> ids);
	
	@RequestMapping(value="/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value="/findList",method = RequestMethod.POST)
	@ResponseBody
	public List<ResBaseAppVersionGroup> findList(@RequestBody Map<String, Object> param);
	
}
