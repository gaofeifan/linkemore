package cn.linkmore.ops.ent.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.request.ReqVipUser;
import cn.linkmore.ops.ent.service.VipUserService;

@RequestMapping(value = "/admin/ent/vip_user")
@Controller
public class VipUserController {

	@Resource
	private VipUserService vipUserService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return vipUserService.findPage(pageable);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqVipUser auth,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.vipUserService.save(auth);
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
	public ViewMsg update(ReqVipUser auth,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.vipUserService.update(auth);
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
			this.vipUserService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

}
