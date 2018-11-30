package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.common.client.hystrix.PayConfigClientHystrix;
import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 远程调用 - 商户配置
 * 
 * @author 常磊
 * @version 2.0
 *
 */
@FeignClient(value = "common-server", path = "/feign/pay", fallback = PayConfigClientHystrix.class, configuration = FeignConfiguration.class)
public interface PayConfigClient {

	@RequestMapping(value="/get",method=RequestMethod.POST)
	@ResponseBody 
	public ResPayConfig getConfig(ReqPayConfig reqPayConfig);
	
}