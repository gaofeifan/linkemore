package cn.linkmore.coupon.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.hystrix.ThemeClientHystrix;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTheme;
import cn.linkmore.coupon.response.ResEnterpriseBean;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 主题
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/theme", fallback=ThemeClientHystrix.class,configuration = FeignConfiguration.class)
public interface ThemeClient {
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTheme record);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTheme record);
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/enterprise_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResEnterpriseBean> enterpriseList();
}
