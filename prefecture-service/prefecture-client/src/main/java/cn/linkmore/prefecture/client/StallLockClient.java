package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallLockClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;
/**
 * 远程调用 - 车位锁
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/ops/stall_lock", fallback=StallLockClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallLockClient {
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStallLock record);

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStallLock record);

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/v2.0/all", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> findAll(@RequestParam("lockId") Long lockId);
	
	@RequestMapping(value = "/v2.0/batch_save", method = RequestMethod.POST)
	@ResponseBody
	public int batchSave(@RequestBody List<ReqStallLock> locks);
	
	@RequestMapping(value = "/v2.0/check_sn", method = RequestMethod.POST)
	@ResponseBody
	public int checkSn(@RequestParam("sn") String sn);
	
	@RequestMapping(value = "/v2.0/check_former_sn", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkFormerSn(@RequestBody Map<String, Object> param);
	
	@RequestMapping(value = "/v2.0/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> findList(@RequestBody Map<String, Object> param);
}
