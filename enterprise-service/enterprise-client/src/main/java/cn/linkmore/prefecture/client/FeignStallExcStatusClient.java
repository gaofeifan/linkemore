package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.FeignEnterpriseClientHystrix;
import cn.linkmore.prefecture.client.hystrix.FeignStallExcStatusClientHystrix;
/**
 * 远程调用 - 企业优惠劵
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/feign/stall/exc", fallback=FeignStallExcStatusClientHystrix.class,configuration = FeignConfiguration.class)
public interface FeignStallExcStatusClient {

	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	List<ResEntExcStallStatus> findAll();

	@RequestMapping(value="/by-stall-id",method=RequestMethod.GET)
	@ResponseBody
	ResEntExcStallStatus findByStallId(@RequestParam("stallId")Long stallId);
	

}
