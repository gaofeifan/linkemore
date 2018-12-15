package cn.linkmore.ops.biz.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.ops.biz.response.ResPrefecture;
import cn.linkmore.ops.biz.service.ApplicationGroupService;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.response.ResPrefectureGroup;
import cn.linkmore.security.request.ReqPerson;


@Controller
@RequestMapping("/admin/biz/application_group")
public class ApplicationGroupController {

	@Resource
	private ApplicationGroupService groupService;

	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return groupService.findPage(pageable);
	}
	/*
	 * 新增
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg add(HttpServletRequest request,ReqApplicationGroup requestBean){
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ReqPerson person = (ReqPerson)subject.getSession().getAttribute("person");
			this.groupService.add(requestBean,person);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.groupService.start(ids);
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
			this.groupService.down(ids);
			msg = new ViewMsg("禁用成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("禁用失败", false);
		}
		return msg;
	}
	/*
	 * 检查名称重复
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property, String value, Long id) {
		return this.groupService.check(property, value, id);
	}
	//用户组列表
	/*@RequestMapping(value = "/user_group_select", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserGroup> userGroupSelect(){
		return this.groupService.getUserGroupSelect();
	}*/
	//车区组列表
	@RequestMapping(value = "/pre_group_select", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefectureGroup> preGroupSelect(){
		return this.groupService.getPreGroupSelect();
	}
	//城市列表
	@RequestMapping(value = "/city_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList(){
		return this.groupService.getCityList();
	}
	//车区列表
	@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefecture> preList(@RequestBody Long cityId){
		return this.groupService.getPreList(cityId);
	}
}
