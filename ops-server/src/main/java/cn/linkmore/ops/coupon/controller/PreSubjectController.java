package cn.linkmore.ops.coupon.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
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
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.ops.coupon.service.PreSubjectService;
import cn.linkmore.ops.security.response.ResPerson;

@Controller
@RequestMapping("/admin/coupon_pre_subject")
public class PreSubjectController {

	@Autowired
	private PreSubjectService preSubjectService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqPreSubject record) {
		ViewMsg msg = null;
		try {
			boolean flag = this.check("pre_id", record.getPreId().toString(), new Date().getTime());
			if(!flag){
				msg = new ViewMsg("此车区已经存在规则", false);
			}else{
				ResPerson person = (ResPerson)SecurityUtils.getSubject().getSession().getAttribute("person");
				record.setOperatorId(person.getId());
				this.preSubjectService.save(record);
				msg = new ViewMsg("保存成功", true);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqPreSubject record) {
		ViewMsg msg = null;
		try {
			boolean flag = this.check("pre_id", record.getPreId().toString(), record.getId());
			if(!flag){
				msg = new ViewMsg("此车区已经存在规则", false);
			}else{
				this.preSubjectService.update(record);
				msg = new ViewMsg("保存成功", true);
			}
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
			for(long id:ids){
				this.preSubjectService.delete(id);
			}
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
		return  this.preSubjectService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.preSubjectService.findPage(pageable);
	}
	
	
	@RequestMapping(value = "/subject_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResBizSubjectBean> subjectList(HttpServletRequest request,Long comboType){
		return this.preSubjectService.subjectList(comboType);
	} 

}
