package cn.linkmore.ops.ent.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.request.ReqBindStaffAuth;
import cn.linkmore.ops.ent.service.StaffService;

/**
 * 员工
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
@RequestMapping(value="/admin/ent/staff")
@Controller
public class StaffController {

	@Resource
	private StaffService staffService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.staffService.findPage(pageable);
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree list(HttpServletRequest request) {
		return this.staffService.tree(request);
	}
	
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind( ReqBindStaffAuth staffAuth) {
		ViewMsg msg = null;
		try {
			this.staffService.bind(staffAuth);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	@ResponseBody
	public ViewMsg resource(Long resource) {
		ViewMsg msg = null;
		try {
			Map<String,Object> map = this.staffService.resource(resource);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
}
