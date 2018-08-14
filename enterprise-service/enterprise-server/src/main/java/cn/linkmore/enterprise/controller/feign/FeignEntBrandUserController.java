package cn.linkmore.enterprise.controller.feign;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.service.EntBrandUserService;

/**
 * 
 * @Description - 品牌用户
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/feign/ent-brand-user")
public class FeignEntBrandUserController {

	@Resource
	private EntBrandUserService entBrandUserService;

	@RequestMapping(value = "/v2.0/check-exist", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkExist(@RequestParam("userId") Long userId, @RequestParam("plateNo") String plateNo) {
		int num = this.entBrandUserService.checkExist(userId, plateNo);
		if (num > 0) {
			return true;
		}
		return false;
	}

}
