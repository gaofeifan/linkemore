package cn.linkmore.coupon.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.coupon.client.hystrix.TemplateItemClientHystrix;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupon_template_item", fallback=TemplateItemClientHystrix.class,configuration = FeignConfiguration.class)
public interface TemplateItemClient {
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateItem record);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateItem record);
}
