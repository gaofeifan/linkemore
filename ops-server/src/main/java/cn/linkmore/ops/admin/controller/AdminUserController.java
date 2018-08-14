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
import cn.linkmore.ops.admin.service.AdminUserService;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminUser;

/**
 * Controller - 线下管理员
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/admin/admin/admin_user")
public class AdminUserController {
	
	@Autowired
	AdminUserService adminUserService;
	/*
	 * 管理员列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.adminUserService.findPage(pageable); 
	} 
	
	/*
	 * 检查
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
        return this.adminUserService.check(reqCheck);
	}
	/*
	 * 新增
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqAdminUser admin){
		ViewMsg msg = null;
		try {
			this.adminUserService.save(admin);
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
	public ViewMsg update(ReqAdminUser admin){
		ViewMsg msg = null;
		try {
			this.adminUserService.update(admin);
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
		return this.adminUserService.findTree();
	}
	/*
	 * 权限回显
	 */
	@RequestMapping(value = "/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(HttpServletRequest request,Long id){ 
		return this.adminUserService.resource(id);
	}
	
	/*
	 * 绑定
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind(HttpServletRequest request,Long id,String authids){ 
		ViewMsg msg = null;
		try {
			this.adminUserService.bind(id,authids);
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
			this.adminUserService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	/**
	 * @Description  查询所有
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/all", method = RequestMethod.GET)
	@ResponseBody
	public List<ResAdminUser> findAll(){
		return this.adminUserService.findAll();
	}
}
