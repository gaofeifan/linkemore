package cn.linkmore.ops.ent.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.controller.BaseController;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.ent.service.RentUserService;

/**
 * 长租用户
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/rent")
public class RentUserController extends BaseController {

	@Resource
	private RentUserService rentUserService;
	
	@Autowired
	private EnterpriseService enterService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(getPerson().getEntId()!= null && getPerson().getEntId() != 0L) {
			param.put("property", "id");
			param.put("value", getPerson().getEntId());
			ResEnterprise enter = enterService.find(param);
			if(enter != null) {
				pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"createEntId",getPerson().getEntId()));
			}
		}
		return this.rentUserService.findList(request,pageable);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqRentUser user) {
		ViewMsg msg = null;
		try {
			user.setCreateTime(new Date());
			user.setCreateUserId(getPerson().getId());
			user.setEntId(getPerson().getEntId());
			user.setEntName(getPerson().getEntName());
			user.setCreateUserName(getPerson().getUsername());
			user.setUpdateTime(new Date());
			user.setUpdateUserId(getPerson().getId());
			user.setUpdateUserName(getPerson().getUsername());
			if(getPerson().getEntId()!= null) {
				user.setCreateEntId(getPerson().getEntId());
				user.setCreateEntName(getPerson().getEntName());
			}
			this.rentUserService.save(user);
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
	public ViewMsg update( ReqRentUser user) {
		ViewMsg msg = null;
		try {
			user.setUpdateTime(new Date());
			user.setUpdateUserId(getPerson().getId());
			user.setUpdateUserName(getPerson().getUsername());
			this.rentUserService.update(user); 
			msg = new ViewMsg("更新成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("更新失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.rentUserService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean update(ReqCheck reqCheck) {
		return this.rentUserService.check(reqCheck);
	}
}
