package cn.linkmore.ops.account.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqStaffAppVersion;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.ops.account.service.StaffAppVersionService;
import cn.linkmore.ops.ent.request.ReqAppVersion;
import cn.linkmore.ops.ent.service.AppVersionService;
import cn.linkmore.ops.security.response.ResPerson;

@RequestMapping(value="/admin/staff/version")
@Controller
public class StaffAppVersionController {

	@Resource
	private StaffAppVersionService staffAppVersionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.staffAppVersionService.findPage(pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStaffAppVersion auth,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
			auth.setCreateTime(new Date());
			auth.setUpdateTime(new Date());
			this.staffAppVersionService.save(auth);
			msg = new ViewMsg("保存成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqStaffAppVersion auth) {
		ViewMsg msg = null;
		try {
			auth.setUpdateTime(new Date());
			this.staffAppVersionService.update(auth);
			msg = new ViewMsg("保存成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.staffAppVersionService.delete(ids);
			msg = new ViewMsg("刪除成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property,String value,Long id){
		return this.staffAppVersionService.check(property, value, id); 
	}
}
