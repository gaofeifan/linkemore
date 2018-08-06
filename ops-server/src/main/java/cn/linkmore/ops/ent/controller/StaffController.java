package cn.linkmore.ops.ent.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntStaff;
import cn.linkmore.enterprise.request.ReqCheck;
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
	
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(Long id) {
		Map<String,Object> map = this.staffService.resource(id);
		return map;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.staffService.delete(ids.get(0));
			msg = new ViewMsg("删除成功", true); 
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqAddEntStaff staff) {
		ViewMsg msg = null;
		try {
			this.staffService.save(staff);
			msg = new ViewMsg("保存成功", true); 
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqAddEntStaff staff) {
		ViewMsg msg = null;
		try {
			this.staffService.update(staff);
			msg = new ViewMsg("修改成功", true); 
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg stop(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.staffService.stop(ids.get(0));
			msg = new ViewMsg("修改成功", true); 
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg start(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.staffService.start(ids.get(0));
			msg = new ViewMsg("修改成功", true); 
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	
	/*
	 * 检查
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
		return this.staffService.check(reqCheck); 
	}
}
