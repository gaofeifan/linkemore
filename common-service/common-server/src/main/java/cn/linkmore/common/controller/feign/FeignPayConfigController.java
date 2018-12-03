package cn.linkmore.common.controller.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.request.ReqPayConfig;
import cn.linkmore.common.response.ResPayConfig;
import cn.linkmore.common.service.PayConfigService;



/**
 *车厂配置商户配置信息
 */ 
@RestController
@RequestMapping("/feign/pay")
public class FeignPayConfigController {

	@Autowired
	PayConfigService payConfigService;
	
	/**
	 *  获取配置信息
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	@ResponseBody 
	public ResPayConfig getConfig(@RequestBody ReqPayConfig reqPayConfig) {
		return payConfigService.getConfig(reqPayConfig);
	}
	
	
}
