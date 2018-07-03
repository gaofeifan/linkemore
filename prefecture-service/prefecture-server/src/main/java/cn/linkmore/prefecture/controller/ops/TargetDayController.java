package cn.linkmore.prefecture.controller.ops;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.service.TargetDayService;
/**
 * Controller - 车区每日目标
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/ops/target_day")
public class TargetDayController { 
	@Autowired
	private TargetDayService targetDayService;
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.targetDayService.findPage(pageable); 
	} 
	
}
