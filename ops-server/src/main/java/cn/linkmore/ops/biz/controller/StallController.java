package cn.linkmore.ops.biz.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StallLockService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;

@Controller
@RequestMapping("/admin/biz/stall")
public class StallController {

	@Resource
	private StallService stallService;
	
	@Resource
	private StallLockService stallLockService;

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request) {
		return stallService.findTree();
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return stallService.findPage(pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(HttpServletRequest request, ReqStall stall) {
		ViewMsg msg = null;
		try {
			this.stallService.save(stall);
			msg = new ViewMsg("保存成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(HttpServletRequest request, ReqStall stall) {
		ViewMsg msg = null;
		try {
			this.stallService.update(stall);
			msg = new ViewMsg("修改成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(HttpServletRequest request, String stallName, Long preId, Long id) {
		boolean flag = true;
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setProperty(stallName);
		reqCheck.setValue(preId.toString());
		reqCheck.setId(id);
		int check = this.stallService.check(reqCheck);
		if (check > 0) {
			flag = false;
		}
		return flag;
	}

	@RequestMapping(value = "/sn", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> sn(HttpServletRequest request, @RequestParam("lockId") Long lockId) {
		return stallLockService.findAll(lockId);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResStallEntity detail(HttpServletRequest request, Long id) {
		return stallService.findById(id);
	}

	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind(HttpServletRequest request, Long id, Long sid) {
		ViewMsg msg = null;
		try {
			this.stallService.bind(id,sid);
			msg = new ViewMsg("绑定成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("绑定失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/changed_up", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg changedUp(HttpServletRequest request, @RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.stallService.changedUp(ids);
			msg = new ViewMsg("上线成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg(e.getMessage(), false);
			return msg;
		} catch (Exception e) {
			msg = new ViewMsg("上线失败", false);
			return msg;
		}
		return msg;
	}

	@RequestMapping(value = "/changed_down", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg changedDown(HttpServletRequest request, Long id) {
		ViewMsg msg = null;
		try {
			this.stallService.changedDown(id);
			msg = new ViewMsg("下线成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg(e.getMessage(), false);
			return msg;
		} catch (Exception e) {
			msg = new ViewMsg("下线失败", false);
			return msg;
		}
		return msg;
	}

}
