package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.FeignLockClientHystrix;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockMessage;

@FeignClient(value = "prefecture-server", path = "/feign/lock", fallback=FeignLockClientHystrix.class,configuration = FeignConfiguration.class)
public interface FeignLockClient {

	
	@RequestMapping(value="/lock-info",method=RequestMethod.GET)
	@ResponseBody
	public ResLockInfo lockInfo(@RequestParam("sn") String sn);
	
	@RequestMapping(value="/lock-list",method=RequestMethod.GET)
	@ResponseBody
	public List<ResLockInfo> lockList(@RequestParam("groupCode") String groupCode);

	@RequestMapping(value="/down-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage downLockMes(@RequestParam("sn") String sn);
	
	@RequestMapping(value="/down-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean downLock(@RequestParam("sn") String sn);
	
	@RequestMapping(value="/up-lock",method=RequestMethod.GET)
	@ResponseBody
	public Boolean upLock(@RequestParam("sn") String sn);
	
	@RequestMapping(value="/up-lock-mes",method=RequestMethod.GET)
	@ResponseBody
	public ResLockMessage upLockMes(@RequestParam("sn") String sn);
}
