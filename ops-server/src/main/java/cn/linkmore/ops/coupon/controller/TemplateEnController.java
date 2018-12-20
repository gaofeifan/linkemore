package cn.linkmore.ops.coupon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.TemplateEnService;
import cn.linkmore.security.response.ResPerson;

@Controller
@RequestMapping("/admin/coupon_enterprise")
public class TemplateEnController {
	
	@Autowired
	private TemplateEnService templateEnService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqTemplate record,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			ResPerson person = getPerson(request);
			record.setCreatorId(person.getId().intValue());
			record.setCreatorName(person.getUsername());
			record.setDeleteStatus(0);
			this.templateEnService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/saveBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg saveBusiness(ReqTemplate record,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			ResPerson person = getPerson(request);
			record.setCreatorId(person.getId().intValue());
			record.setCreatorName(person.getUsername());
			record.setDeleteStatus(0);
			this.templateEnService.saveBusiness(record);
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
	public ResTemplate detail(@RequestBody Long id) {
		return this.templateEnService.findById(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTemplate record,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			ResPerson person = getPerson(request);
			record.setCreatorId(person.getId().intValue());
			record.setCreatorName(person.getUsername());
			this.templateEnService.update(record);
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
			this.templateEnService.delete(ids.get(0));
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
		return this.templateEnService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.templateEnService.findPage(pageable);
	}
	
	@RequestMapping(value = "/listBusiness", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listBusiness(HttpServletRequest request, ViewPageable pageable) {
		 ResPerson person = getPerson(request);
		 List<ViewFilter> filters = pageable.getFilters();
		 ViewFilter vf = new ViewFilter();
		 vf.setProperty("enterpriseId");
		 vf.setValue(person.getId());
		 vf.setValue(0L);
		 filters.add(vf);
		 vf = new ViewFilter();
		 vf.setProperty("isBusinessSelect");
		 vf.setValue(1);
		 filters.add(vf);
		return this.templateEnService.findPage(pageable);
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg start(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.templateEnService.start(id);
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
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg down(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.templateEnService.stop(id);
			msg = new ViewMsg("暂停成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("暂停失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/selectByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplate> selectByEnterpriseId( Long id ,HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		return this.templateEnService.findByEnterpriseId(person.getId());
	}
	
	/**
	 * 下载二维码
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void download(Long id,HttpServletResponse response){
	}
	

	private ResPerson getPerson(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		return person;
	}
}
