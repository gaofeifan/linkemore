package cn.linkmore.enterprise.controller.ops;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.service.EntBrandApplicantService;

/**
 * @Description - 品牌车位
 * @Author jiaohanbin
 * @Version v2.0
 */
@Controller
@RequestMapping("/ops/ent-brand-applicant")
public class EntBrandApplicantController {
	
	@Resource
	private EntBrandApplicantService entBrandApplicantService;

	/**
	 * 车区列表分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.entBrandApplicantService.findPage(pageable);
	}

}
