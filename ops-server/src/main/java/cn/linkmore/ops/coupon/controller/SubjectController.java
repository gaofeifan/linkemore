package cn.linkmore.ops.coupon.controller;

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
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.SubjectService;
import cn.linkmore.ops.security.response.ResPerson;

@Controller
@RequestMapping("/admin/coupon_subject_new")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqSubject record) {
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			record.setOperatorId(person.getId());
			this.subjectService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResSubject detail(@RequestBody Long id) {
		ResSubject couponSubject = this.subjectService.findById(id);
		return couponSubject;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqSubject record) {
		ViewMsg msg = null;
		try {
			this.subjectService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
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
			this.subjectService.delete(ids.get(0));
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property, String value, Long id) {
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setProperty(property);
		reqCheck.setValue(value);
		reqCheck.setId(id);
		return this.subjectService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.subjectService.findPage(pageable);
	}
	
	/*
	 * 上线
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.subjectService.start(id);
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
	 * 下线
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg down(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.subjectService.stop(id);
			msg = new ViewMsg("暂停成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("暂停失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/subject_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplate> subjectList(HttpServletRequest request){
		return this.subjectService.subjectList();
	}
}
