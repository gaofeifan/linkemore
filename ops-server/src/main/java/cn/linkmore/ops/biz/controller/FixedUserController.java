package cn.linkmore.ops.biz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.ops.biz.service.FixedUserService;
import cn.linkmore.prefecture.request.ReqStall;

@RestController
@RequestMapping("/admin/biz/fixed/user")
public class FixedUserController  extends BaseController{

	@Autowired
     private FixedUserService fixedUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(HttpServletRequest request, ViewPageable pageable) {
		
		System.out.println("fixed-user session:pre_id="+getPerson().getPreId());
		pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"createUserId",getPerson().getPreId()));
		return this.fixedUserService.findList(request,pageable);
	}
	
	@RequestMapping(value = "/pick", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg pick(HttpServletRequest request, ReqFixedUserPick reqFixedUserPick) {
		ViewMsg msg = null;
		try {
			this.fixedUserService.pick(request, reqFixedUserPick);
			msg = new ViewMsg("修改成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
}
