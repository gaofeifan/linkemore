package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsEntBrandAdClientHystrix;
/**
 * 远程调用 - 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/ops/ent-brand-ad", fallback=OpsEntBrandAdClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsEntBrandAdClient {
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
	public int save(@RequestBody ReqEntBrandAd reqEntBrandAd);
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEntBrandAd reqEntBrandAd);
	
	@RequestMapping(value = "/v2.0/find", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandAd findById(@RequestParam("id") Long id);

	/**
	 * 删除
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);

	/*
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.GET)
	@ResponseBody
	public int start(@RequestParam("id") Long id);
	
	/*
	 * 禁用
	 */
	@RequestMapping(value = "/v2.0/stop", method = RequestMethod.GET)
	@ResponseBody
	public int stop(@RequestParam("id") Long id) ;
}
