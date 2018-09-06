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
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.ops.ent.service.RentUserService;

/**
 * 长租用户
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/ent/rent")
public class RentUserController {

	@Resource
	private RentUserService rentUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		return this.rentUserService.findList(request,pageable);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqRentUser auth) {
		ViewMsg msg = null;
		try {
//			ReqRentUser auth = new ReqRentUser();
			this.rentUserService.save(auth);
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
	public ViewMsg update( ReqRentUser auth) {
		ViewMsg msg = null;
		try {
			this.rentUserService.update(auth);
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
