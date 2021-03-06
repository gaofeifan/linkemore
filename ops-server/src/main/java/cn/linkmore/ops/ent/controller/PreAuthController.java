package cn.linkmore.ops.ent.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.ops.ent.service.OperateService;
import cn.linkmore.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RequestMapping(value="/admin/ent/operate-auth")
@Controller
public class PreAuthController {

	@Resource
	private OperateService operateService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.operateService.findPage(pageable);
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree(HttpServletRequest request) {
		return this.operateService.tree(request);
	}
	
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind(@RequestParam("auth") String auth) {
		ViewMsg msg = null;
		try {
			ReqOperateBind bind = JsonUtil.toObject(auth, ReqOperateBind.class);
			this.operateService.bind(bind);
			msg = new ViewMsg("绑定成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("绑定失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(Long id) {
		Map<String,Object> map = this.operateService.resource(id);
		return map;
	}
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save( ReqOperateAuth auth) {
		ViewMsg msg = null;
		try {
			this.operateService.save(auth);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update( ReqOperateAuth auth) {
		ViewMsg msg = null;
		try {
			this.operateService.update(auth);
			msg = new ViewMsg("更新成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("更新失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg start(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.operateService.start(ids.get(0));
			msg = new ViewMsg("更新成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("更新失败", false);
		}
		return msg;
	}
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg stop(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.operateService.stop(ids.get(0));
			msg = new ViewMsg("更新成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("更新失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.operateService.delete(ids.get(0));
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
}
