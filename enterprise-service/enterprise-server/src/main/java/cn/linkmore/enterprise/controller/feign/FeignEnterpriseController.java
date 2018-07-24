package cn.linkmore.enterprise.controller.feign;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EnterpriseService;

/**
 * 
 * @Description - 企业
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/feign/enterprise")
public class FeignEnterpriseController {
	
	@Resource
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ResponseBody
	public ResEnterprise findById(@RequestParam("id") Long id) {
		return	this.enterpriseService.findById(id);
	}

}
