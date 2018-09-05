package cn.linkmore.prefecture.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntStaffClientHystrix;
/**
 * 远程调用 - 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/staff", fallback=EntStaffClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntStaffClient {
	@RequestMapping(value = "/by-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntStaff findById(@RequestParam("id") Long id);
	
}
