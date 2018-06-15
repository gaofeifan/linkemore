package cn.linkmore.coupon.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.hystrix.TemplateConditionClientHystrix;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 条件
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupon_template_condition", fallback=TemplateConditionClientHystrix.class,configuration = FeignConfiguration.class)
public interface TemplateConditionClient {
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateCondition record);

	/**
	 * 禁用和启用
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateCondition record);

	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	@RequestMapping(value = "/v2.0/setDefault", method = RequestMethod.GET)
	@ResponseBody
	public int setDefault(@RequestParam("id") Long id);

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) ;

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/condition_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResTemplateCondition> conditionList(@RequestParam("tempId") Long tempId);
	
	
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResTemplateCondition detail(@RequestParam("id") Long id);
}
