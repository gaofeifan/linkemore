package cn.linkmore.ops.admin.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.TargetDayService;
/**
 * Controller - 车区每日目标
 * @author jiaohanbin
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/pre_target_day")
public class TargetDayController { 
	@Autowired
	private TargetDayService targetDayService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.targetDayService.findPage(pageable); 
	} 
	
}
