package cn.linkmore.ops.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.service.StaffService;

/**
 * 员工
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
@RequestMapping(value="/ent/staff")
@RestController
public class StaffController {

	@Resource
	private StaffService staffService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.staffService.findPage(pageable);
	}
}
