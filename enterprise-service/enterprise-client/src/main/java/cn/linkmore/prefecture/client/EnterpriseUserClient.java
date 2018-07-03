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

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EnterpriseUserClientHystrix;
/**
 * 远程调用 - 
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/enterprise_user", fallback=EnterpriseUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface EnterpriseUserClient {
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqEnterpriseUser enterpriseUser);

	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id);

	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public List<ResEnterpriseUser> export(@RequestParam("userName") String userName);

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public ResEnterpriseUser getEnterpriseUser(@PathVariable("id")Long id);

	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public List<ResEnterpriseUser> findExcel(@RequestBody Map<String, Object> param);

	@RequestMapping(value = "/save-all", method = RequestMethod.POST)
	public void saveAll(@RequestBody List<ReqEnterpriseUser> entAll);
	
}
