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
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EnterpriseDealClientHystrix;
/**
 * 远程调用 - 企业合同
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/enterprise_deal", fallback=EnterpriseDealClientHystrix.class,configuration = FeignConfiguration.class)
public interface EnterpriseDealClient {
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqEnterpriseDeal deal);
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEnterpriseDeal deal);
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable); 

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree();
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> map();
	
	@RequestMapping(value = "/listByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterpriseDeal> listByEnterpriseId(@RequestBody Map<String, Object> map);
	
	@RequestMapping(value = "/listByEnterpriseId/{number}", method = RequestMethod.GET)
	@ResponseBody
	public ResEnterpriseDeal selectByDealNumber(@PathVariable("number")String number);

	@RequestMapping(value = "/create-status", method = RequestMethod.POST)
	@ResponseBody
	public void updateCreateStatus(@RequestBody Map<String, Object> map);
}
