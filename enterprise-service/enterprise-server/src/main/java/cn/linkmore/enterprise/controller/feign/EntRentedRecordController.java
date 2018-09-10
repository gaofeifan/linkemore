package cn.linkmore.enterprise.controller.feign;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.service.EntRenedRecordService;

@Controller
@RequestMapping("/feign/ent-rented")
public class EntRentedRecordController {

	@Resource
	private EntRenedRecordService entRenedRecordService;
	@RequestMapping(value = "/down-time", method = RequestMethod.GET)
	@ResponseBody
	public void updateDownTime(@RequestParam("stallId") Long stallId) {
		this.entRenedRecordService.updateDownTime(stallId);
	}
}
