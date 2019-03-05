package cn.linkmore.enterprise.controller.feign;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.enterprise.response.ResFixedPlate;
import cn.linkmore.enterprise.service.FixedPlateService;

/**
 * 调用车牌信息
 * @author   jiaohanbin
 * @Date     2019年3月4日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/fixed-plate") 
public class FeignFixedPlateController {

	@Resource
	private FixedPlateService fixedPlateService;
	
	@RequestMapping(value = "/find-plate-nos", method = RequestMethod.GET)
	@ResponseBody
	public ResFixedPlate findPlateNosByStallId(@RequestParam("stallId") Long stallId) {
		return this.fixedPlateService.findPlateNosByStallId(stallId);
	}
}
