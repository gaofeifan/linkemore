package cn.linkmore.enterprise.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.Enterprise;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.service.EnterpriseService;

/**
 * 
 * @Description - 企业
 * @Author jiaohanbin
 * @Date 2018年1月20日 上午11:50:26
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/admin/biz/enterprise")
public class EnterpriseController {
	@Resource
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(ReqEnterprise record) {
		return this.enterpriseService.save(record);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(ReqEnterprise record) {
		return this.enterpriseService.update(record);
	}

	/*@RequestMapping(value = "/set_password", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg setPassword(Person person) {
		ViewMsg msg = null;
		try {
			this.enterpriseService.setPassword(person);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}*/

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestParam("id") Long id) {
		return	this.enterpriseService.delete(id);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.enterpriseService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.enterpriseService.findPage(pageable);
	}
	
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		return this.enterpriseService.selectAll();
	}

}