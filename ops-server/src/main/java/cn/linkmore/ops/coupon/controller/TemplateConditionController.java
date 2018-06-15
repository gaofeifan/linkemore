package cn.linkmore.ops.coupon.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
import cn.linkmore.ops.coupon.service.TemplateConditionService;

@Controller
@RequestMapping("/admin/coupon_template_condition")
public class TemplateConditionController {

	@Autowired
	private TemplateConditionService templateConditionService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqTemplateCondition record) {
		ViewMsg msg = null;
		try {
			this.templateConditionService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	/**
	 * 禁用和启用
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTemplateCondition record) {
		ViewMsg msg = null;
		try {
			this.templateConditionService.update(record);
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
			this.templateConditionService.delete(ids.get(0));
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/setDefault", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg setDefault(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.templateConditionService.setDefault(id);
			msg = new ViewMsg("设置成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("设置失败", false);
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
		return this.templateConditionService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.templateConditionService.findPage(pageable);
	}
	
	@RequestMapping(value = "/condition_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTemplateCondition> conditionList(@RequestBody Long tempId) {
		List<ResTemplateCondition> list = this.templateConditionService.findConditionList(tempId);
		return list;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResTemplateCondition detail(@RequestBody Long id){
		return this.templateConditionService.detail(id);
	}

}
