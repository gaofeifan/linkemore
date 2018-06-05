package cn.linkmore.prefecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.TargetMonthService;
/**
 * Controller - 车区每月目标设置
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/target_month")
public class TargetMonthController { 
	@Autowired
	private TargetMonthService targetMonthService;
	
	@Autowired
	private PrefectureService preService;
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.targetMonthService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree() {
		return preService.findTree();
	}
	
}
