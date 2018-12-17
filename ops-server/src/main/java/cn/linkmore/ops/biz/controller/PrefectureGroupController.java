package cn.linkmore.ops.biz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PreGroupService;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;
import cn.linkmore.security.response.ResPerson;


/**
 * 
 * @Description - 专区分组
 * @Author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/admin/biz/pre_group")
public class PrefectureGroupController {

	@Autowired
	private PreGroupService preGroupService;

	/*
	 * 检查名称重复
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck) {
		return this.preGroupService.check(reqCheck);
	}

	/*
	 * 新增
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqPrefectureGroup preGroup) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			preGroup.setOperatorId(person.getId());
			this.preGroupService.save(preGroup);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;

	}
	
	/*
	 * 信息列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.preGroupService.findPage(pageable);
	}
	
	/*
	 * 启用
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.preGroupService.start(ids);
			msg = new ViewMsg("启用成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("启用失败", false);
		}
		return msg;
	}
	/*
	 * 禁用
	 */
	@RequestMapping(value = "/down", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg down(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.preGroupService.down(ids);
			msg = new ViewMsg("禁用成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("禁用失败", false);
		}
		return msg;
	}
	
}
