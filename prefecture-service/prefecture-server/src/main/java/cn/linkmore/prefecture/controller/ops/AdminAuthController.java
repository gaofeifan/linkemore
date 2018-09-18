package cn.linkmore.prefecture.controller.ops;

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
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResStaffCity;
import cn.linkmore.prefecture.service.AdminAuthService;


/**
 * Controller - 线下管理员 授权
 * @author jiaohanbin
 */
@Controller
@RequestMapping("/ops/admin_auth")
public class AdminAuthController {
	
	@Autowired
	AdminAuthService adminAuthService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.adminAuthService.findPage(pageable); 
	} 
	
	/**
	 * 检查
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.adminAuthService.check(reqCheck); 
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
	public int save(@RequestBody ReqAdminAuth admin){
		return this.adminAuthService.save(admin);
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqAdminAuth admin){
		return this.adminAuthService.update(admin);
	}
	/**
	 * 资源树
	 */
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(){ 
		return this.adminAuthService.findTree();
	}
	/*
	 * 权限回显
	 */
	@RequestMapping(value = "/v2.0/resource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resource(@RequestParam("id") Long id){ 
		return this.adminAuthService.resource(id);
	}
	
	/*
	 * 绑定
	 */
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestParam("id") Long id,@RequestParam("citys") String citys,@RequestParam("pres") String pres,@RequestParam("json") String json){ 
		this.adminAuthService.bind(id,json,citys,pres);
	}
	/*
	 * 删除
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		return	this.adminAuthService.delete(ids);
	}
	

	@RequestMapping(value = "/by-admin-id", method = RequestMethod.GET)
	@ResponseBody
	public List<ResStaffCity> findStaffCitysByAdminId(@RequestParam("id")Long id){
		return this.adminAuthService.findStaffCitysByAdminId(id);
	}
}
