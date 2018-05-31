package cn.linkmore.ops.security.controller;
 
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
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqMenu;
import cn.linkmore.ops.security.service.MenuService;

 

/**
 * Controller - 模块模块  - 菜单信息
 * @author liwenlong
 * @version 1.0
 *
 */

@Controller
@RequestMapping("/admin/security/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqMenu menu){
		ViewMsg msg = null;
		try { 
			this.menuService.save(menu);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqMenu menu){
		ViewMsg msg = null;
		try {
			this.menuService.update(menu);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.menuService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
		Boolean flag = this.menuService.check(reqCheck); 
        return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){ 
		return this.menuService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request){ 
		return this.menuService.findTree();
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> map(HttpServletRequest request){ 
		return this.menuService.map();
	}
}
