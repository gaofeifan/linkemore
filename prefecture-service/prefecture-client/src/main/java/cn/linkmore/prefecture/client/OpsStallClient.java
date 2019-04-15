package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsStallClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
/**
 * 远程调用 - 车位操作
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/ops/stall", fallback=OpsStallClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsStallClient {	
	
	/**
	 * 根据车位id获取车位信息
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/{stallId}", method=RequestMethod.GET)
	@ResponseBody
	public ResStallEntity findById(@PathVariable("stallId") Long stallId);
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(@RequestBody Map<String,Object> param);

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStall stall) ;

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStall stall);

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public int check(@RequestBody ReqCheck reqCheck);

	@RequestMapping(value = "/v2.0/sn", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> sn(@RequestParam("lockId") Long lockId);

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResStallEntity detail(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public int bind(@RequestParam("id") Long id, @RequestParam("sid") Long sid);
	
	@RequestMapping(value = "/v2.0/unBind", method = RequestMethod.POST)
	@ResponseBody
	public int unBind(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/changed_up", method = RequestMethod.POST)
	@ResponseBody
	public int changedUp(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/changed_down", method = RequestMethod.POST)
	@ResponseBody
	public int changedDown(@RequestBody List<Long> ids);
	
	@RequestMapping(value = "/v2.0/save_bind", method = RequestMethod.POST)
	@ResponseBody
	public void saveAndBind(@RequestBody ReqStall reqStall);
	
	@RequestMapping(value = "/v2.0/find-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOps> findList(@RequestBody Map<String, Object> param);

	@RequestMapping(value = "/v2.0/find-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findStallList(@RequestBody Map<String, Object> param);

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);
}
