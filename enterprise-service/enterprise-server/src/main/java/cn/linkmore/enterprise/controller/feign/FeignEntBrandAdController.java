package cn.linkmore.enterprise.controller.feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.enterprise.service.EntBrandAdService;

/**
 * 
 * @Description - 企业品牌广告
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/feign/ent-brand-ad")
public class FeignEntBrandAdController {
	
	@Resource
	private EntBrandAdService entBrandAdService;
	
	@RequestMapping(value = "/v2.0/find", method = RequestMethod.GET)
	@ResponseBody
	public ResBrandAd findByEntId(@RequestParam("entId") Long entId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("entId", entId);
		List<ResBrandAd> adList = this.entBrandAdService.findList(param);
		if(CollectionUtils.isNotEmpty(adList)) {
			return adList.get(0);
		}
		return null;
	}

}
