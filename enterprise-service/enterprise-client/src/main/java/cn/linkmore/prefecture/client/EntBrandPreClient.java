package cn.linkmore.prefecture.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntBrandPreClientHystrix;
/**
 * 远程调用 - 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/ent-brand-pre", fallback=EntBrandPreClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntBrandPreClient {
	@RequestMapping(value = "/v2.0/pre-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBrandPreStall> preStallList();
	
	@RequestMapping(value = "/v2.0/find", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandPre findById(@RequestParam("id") Long id);
	
	@RequestMapping(value = "/v2.0/brand-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBrandStall> brandStallList(@RequestParam("id") Long id);
}
