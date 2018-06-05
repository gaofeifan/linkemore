package cn.linkmore.ops.admin.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.AdminAuthService;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;

/**
 * Controller - 线下管理员 授权
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/admin/admin/admin_auth")
public class AdminAuthController {
	
	@Autowired
	AdminAuthService adminAuthService;
	/*
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.adminAuthService.findPage(pageable); 
	} 
	
	/*
	 * 检查
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
		return this.adminAuthService.check(reqCheck); 
	}
	/*
	 * 新增
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqAdminAuth admin){
		ViewMsg msg = null;
		try {
			this.adminAuthService.save(admin);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(RuntimeException r){
			msg = new ViewMsg(r.getMessage(), false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	/*
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqAdminAuth admin){
		ViewMsg msg = null;
		try {
			this.adminAuthService.update(admin);
			msg = new ViewMsg("更新成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("更新失败",false);
		}
		return msg;
	}
	/*
	 * 资源树
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request){ 
		return this.adminAuthService.findTree();
	}
	/*
	 * 权限回显
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(HttpServletRequest request,Long id){ 
		return this.adminAuthService.resource(id);
	}
	
	/*
	 * 绑定
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind(HttpServletRequest request,Long id,String citys,String pres,String json){ 
		ViewMsg msg = null;
		try {
			this.adminAuthService.bind(id,json,citys,pres);
			msg = new ViewMsg("绑定成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("绑定失败",false);
		}
		return msg;
	}
	/*
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.adminAuthService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
}
