package cn.linkmore.enterprise.controller.feign;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;
import cn.linkmore.enterprise.service.EntBrandPreService;

/**
 * 
 * @Description - 品牌企业车区
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/feign/ent-brand-pre")
public class FeignEntBrandPreController {
	
	@Resource
	private EntBrandPreService entBrandPreService;

	@RequestMapping(value = "/v2.0/pre-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBrandPreStall> preStallList() {
		return	this.entBrandPreService.preStallList();
	}
	
	@RequestMapping(value = "/v2.0/find", method = RequestMethod.POST)
	@ResponseBody
	public ResBrandPre findById(@RequestParam("id") Long id) {
		return this.entBrandPreService.findById(id);
	}

}
