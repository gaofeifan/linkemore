package cn.linkmore.ops.biz.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.security.request.ReqPerson;
/**
 * 企业
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/enterprise")
public class EnterpriseController {
	@Resource
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEnterprise record) {
		ViewMsg msg = null;
		try {
			this.enterpriseService.save(record);
			msg = new ViewMsg("保存成功", true);
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
	public ViewMsg update(ReqEnterprise record) {
		ViewMsg msg = null;
		try {
			this.enterpriseService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/set_password", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg setPassword(ReqPerson person) {
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
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(Long id) {
		ViewMsg msg = null;
		try {
			this.enterpriseService.delete(id);
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
		return this.enterpriseService.check(property, value, id);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.enterpriseService.findPage(pageable);
	}
	
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEnterprise> selectAll() {
		return this.enterpriseService.selectAll();
	}

}
