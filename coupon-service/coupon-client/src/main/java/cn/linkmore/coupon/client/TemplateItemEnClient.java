package cn.linkmore.coupon.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.coupon.client.hystrix.TemplateItemEnClientHystrix;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 企业停车券
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/ops/coupon_enterprise_Item", fallback=TemplateItemEnClientHystrix.class,configuration = FeignConfiguration.class)
public interface TemplateItemEnClient {
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateItem record);
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateItem record);
	
	@RequestMapping(value = "/selectBuDealNumber", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplateItem> selectBuDealNumber(@RequestParam("dealNumber") String dealNumber);
	
	@RequestMapping(value = "/selectByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplateItem> selectBuEnterpriseId(@RequestParam("id") Long id);
}
