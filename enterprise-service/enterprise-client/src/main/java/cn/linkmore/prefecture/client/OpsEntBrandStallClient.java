package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandStall;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsEntBrandStallClientHystrix;
/**
 * 远程调用 - 企业品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/ops/ent-brand-stall", fallback=OpsEntBrandStallClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsEntBrandStallClient {
	/**
	 * 车区列表分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqEntBrandStall reqEntBrandStall);
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEntBrandStall reqEntBrandStall);
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree();
}
