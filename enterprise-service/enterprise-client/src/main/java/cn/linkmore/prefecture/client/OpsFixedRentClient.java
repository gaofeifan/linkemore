package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsFixedRentClientHystrix;

@FeignClient(value = "enterprise-server", path = "/ops/fixed/rent", fallback = OpsFixedRentClientHystrix.class, configuration = FeignConfiguration.class)
public interface OpsFixedRentClient {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestBody Map<String, Object> map);
	
	@RequestMapping(value = "/free_stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> freeStallList(@RequestBody Map<String, Object> map);
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insert(@RequestBody ReqFixedRent reqFixedRent);
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqFixedRent reqFixedRent);
	
	@RequestMapping(value = "/delete/stall", method = RequestMethod.POST)
	@ResponseBody
	public int deleteStall(Map<String, Object> map);

	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public int open(Map<String, Object> map);
	
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public int close(Map<String, Object> map);
	
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResFixedRent view(@RequestBody Long fixedId);

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public String check(@RequestBody ReqFixedRent reqFixedRent);


}
