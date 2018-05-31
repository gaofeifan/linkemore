package cn.linkmore.ops.security.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.service.LogService;


/**
 * Controller - 后台日志
 * @author liwenlong
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/security/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	/*
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.logService.findPage(pageable);
	} 
}
