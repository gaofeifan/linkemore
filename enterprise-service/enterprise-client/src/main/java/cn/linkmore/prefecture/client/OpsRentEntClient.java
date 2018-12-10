package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsRentEntClientHystrix;
@FeignClient(value = "enterprise-server", path = "/ops/rent-ent", fallback=OpsRentEntClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsRentEntClient {
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqRentEnt ent);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqRentEnt ent);
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/stall-company", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage stallListCompany(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> tree(@RequestParam("entId") Long entId);

	/**
	 * 更改状态 开启/关闭
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map);

	@RequestMapping(value = "/v2.0/delete_stall", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStall(@RequestBody List<Long> ids);
	
	@RequestMapping(value = "/v2.0/save_stall", method = RequestMethod.POST)
	@ResponseBody
	public void saveStall(@RequestBody ReqRentEnt ent);

	@RequestMapping(value = "/v2.0/occuy-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<Long> occuyStallList(@RequestBody Map<String, Object> param);
	
}
