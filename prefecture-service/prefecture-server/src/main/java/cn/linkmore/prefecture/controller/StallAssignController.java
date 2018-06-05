package cn.linkmore.prefecture.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.service.StallAssignService;
/**
 * Controller - 车位指定
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/stall_assign")
public class StallAssignController {
	@Resource
	private StallAssignService stallAssignService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.stallAssignService.findPage(pageable);
	}
}
