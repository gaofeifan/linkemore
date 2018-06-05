package cn.linkmore.prefecture.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.service.AdminUserService;

/**
 * Controller - 线下管理员
 * @author jiaohanbin
 */
@Controller
@RequestMapping("/admin_user")
public class AdminUserController {
	
	@Autowired
	AdminUserService adminUserService;
	/**
	 * 管理员列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.adminUserService.findPage(pageable); 
	} 
	
	/**
	 * 检查
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.adminUserService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqAdminUser admin){
		return	this.adminUserService.save(admin);
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqAdminUser admin){
		return	this.adminUserService.update(admin);
	}
	/**
	 * 资源树
	 */
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(){ 
		return this.adminUserService.findTree();
	}
	/**
	 * 权限回显
	 */
	@RequestMapping(value = "/v2.0/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(@RequestParam("id") Long id){ 
		return this.adminUserService.resource(id);
	}
	
	/**
	 * 绑定
	 */
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestParam("id") Long id,@RequestParam("authids") String authids){ 
			this.adminUserService.bind(id,authids);
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		return	this.adminUserService.delete(ids);
	}
}
